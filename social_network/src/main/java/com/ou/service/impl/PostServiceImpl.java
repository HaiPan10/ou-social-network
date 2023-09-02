package com.ou.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ou.pojo.ImageInPost;
import com.ou.pojo.InvitationGroup;
import com.ou.pojo.Post;
import com.ou.pojo.PostInvitation;
import com.ou.pojo.PostInvitationUser;
import com.ou.pojo.PostSurvey;
import com.ou.pojo.Question;
import com.ou.pojo.User;
import com.ou.repository.interfaces.PostRepository;
import com.ou.service.interfaces.CloudinaryService;
import com.ou.service.interfaces.CommentService;
import com.ou.service.interfaces.GroupService;
import com.ou.service.interfaces.ImageInPostService;
import com.ou.service.interfaces.PostInvitationService;
import com.ou.service.interfaces.PostReactionService;
import com.ou.service.interfaces.PostService;
import com.ou.service.interfaces.PostSurveyService;
import com.ou.service.interfaces.QuestionService;
import com.ou.service.interfaces.UserService;
import com.ou.utils.CloudinaryUtils;

@Service
@Transactional(rollbackFor = Exception.class)
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private ImageInPostService imageInPostService;
    @Autowired
    private PostReactionService postReactionService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private PostSurveyService postSurveyService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostInvitationService postInvitationService;
    @Autowired
    private GroupService groupService;

    @Override
    public Post uploadPost(String postContent, Integer userId, List<MultipartFile> images, boolean isActiveComment)
            throws Exception {
        Post newPost = new Post();
        if (postContent.length() == 0 && (images == null || images.get(0).getSize() == 0)) {
            throw new Exception("Empty post!");
        }
        newPost.setContent(postContent);
        newPost.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        newPost.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        newPost.setIsActiveComment(isActiveComment);
        postRepository.uploadPost(newPost, userId);
        if (images != null && images.get(0).getSize() != 0) {
            newPost.setImageInPostList(imageInPostService.uploadImageInPost(images, newPost));
        }
        postReactionService.countReaction(newPost, userId);
        newPost.setCommentTotal(commentService.countComment(newPost.getId()));
        return newPost;
    }

    @Override
    public List<Post> loadPost(Integer userId, Integer currentUserId, Map<String, String> params) throws Exception {
        Optional<List<Post>> listPostOptional = postRepository.loadPost(userId, params, currentUserId);
        if (listPostOptional.isPresent()) {
            List<Post> posts = listPostOptional.get();
            posts.forEach(p -> {
                postReactionService.countReaction(p, currentUserId);
                p.setCommentTotal(commentService.countComment(p.getId()));
                p.getImageInPostList().forEach(img -> img
                        .setContentType(String.format("image/%s", CloudinaryUtils.getImageType(img.getImageUrl()))));
            });
            return posts;
        } else {
            throw new Exception("User không hợp lệ!");
        }
    }

    @Override
    public boolean update(Post post, List<MultipartFile> images, boolean isEditImage) throws Exception {
        Post persistPost = retrieve(post.getId());
        if (!persistPost.getUserId().getId().equals(post.getUserId().getId())) {
            throw new Exception("Not owner");
        } else if (isEditImage) {
            List<ImageInPost> imageInPostList = persistPost.getImageInPostList();
            if (imageInPostList.size() != 0) {
                imageInPostService.deleteImageInPost(imageInPostList);
            }
            if (images != null) {
                persistPost.setImageInPostList(imageInPostService.uploadImageInPost(images, persistPost));
            } else {
                imageInPostList.clear();
                persistPost.setImageInPostList(imageInPostList);
            }
        } else {
        }
        return postRepository.update(persistPost, post);
    }

    @Override
    public Post retrieve(Integer postId) throws Exception {
        System.out.println("[DEBUG] - INSIDE THE POST SERVICE");
        Optional<Post> postOptional = postRepository.retrieve(postId);
        if (postOptional.isPresent()) {
            return postOptional.get();
        } else {
            throw new Exception("Không tìm thấy bài đăng!");
        }
    }

    @Override
    public boolean delete(Integer postId, Integer userId) throws Exception {
        Post persistPost = retrieve(postId);
        if (!persistPost.getUserId().getId().equals(userId) && userId != 1) {
            throw new Exception("Not owner");
        }
        List<String> oldImageUrls = persistPost.getImageInPostList().stream().map(img -> img.getImageUrl())
                .collect(Collectors.toList());
        System.out.println("GOT PERSIST: " + persistPost);
        if (postRepository.delete(persistPost)) {
            oldImageUrls.forEach(oldImage -> {
                try {
                    cloudinaryService.deleteImage(oldImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        return true;
    }

    @Override
    public List<Post> loadNewFeed(Integer currentUserId, @RequestParam Map<String, String> params) throws Exception {
        Optional<List<Post>> listPostOptional = postRepository.loadNewFeed(currentUserId, params);
        if (listPostOptional.isPresent() && listPostOptional.get().size() != 0) {
            List<Post> posts = listPostOptional.get();
            posts.forEach(p -> {
                postReactionService.countReaction(p, currentUserId);
                p.setCommentTotal(commentService.countComment(p.getId()));
                p.getImageInPostList().forEach(img -> img
                        .setContentType(String.format("image/%s", CloudinaryUtils.getImageType(img.getImageUrl()))));
            });
            return posts;
        } else {
            throw new Exception("No more post");
        }
    }

    @Override
    public List<Post> list(Map<String, String> params) {
        return postRepository.list(params);
    }

    @Override
    public Integer countPosts(Map<String, String> params) {
        return postRepository.countPosts(params);
    }

    @Override
    public List<Post> search(Map<String, String> params) {
        return postRepository.search(params);
    }

    @Override
    public Post uploadPostSurvey(Post post, Integer userId) throws Exception {
        if (post.getPostSurvey().getQuestions() == null || post.getPostSurvey().getQuestions().size() == 0) {
            throw new Exception("Empty question!");
        }
        post.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        post.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        PostSurvey postSurvey = post.getPostSurvey();
        post.setPostSurvey(null);
        postRepository.uploadPost(post, userId);
        List<Question> questions = postSurvey.getQuestions();
        postSurvey.setQuestions(null);
        postSurvey = postSurveyService.uploadPostSurvey(post.getId(), postSurvey);
        questionService.create(postSurvey, questions);
        postSurvey.setQuestions(questions);
        post.setPostSurvey(postSurvey);
        return post;
    }

    @Override
    public Post uploadPostInvitation(Post post, Integer userId) throws Exception {
        System.out.println("[DEBUG] - START TO CREATE INVITATION POST");
        post.setCreatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        post.setUpdatedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        PostInvitation postInvitation = post.getPostInvitation();
        post.setPostInvitation(null);
        postRepository.uploadPost(post, userId);

        List<Integer> listUserId = null;
        List<PostInvitationUser> list = postInvitation.getPostInvitationUsers();
        if (list != null) {
            if (list.size() > 0) {
                listUserId = list.stream()
                        .map(p -> Integer.valueOf(p.getUserId().getId())).collect(Collectors.toList());
            } else {
                throw new Exception("Empty invitation user!");
            }
        }

        List<User> listUsers = userService.list(listUserId);
        postInvitation.setPostInvitationUsers(null);
        InvitationGroup group = postInvitation.getGroupId();
        postInvitation.setGroupId(null);
        postInvitation = postInvitationService.create(post.getId(), postInvitation, listUsers);

        if(group != null && listUsers != null){
            group = groupService.create(group);
            groupService.addUsers(group.getId(), listUsers);
        }

        if(listUsers == null){
            // fetch all user
            System.out.println("[DEBUG] - FETCH ALL USER");
            listUsers = userService.list();
        }

        post.setPostInvitation(postInvitation);
        return post;
    }
}
