package com.ou.repository.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.ou.pojo.ImageInPost;
import com.ou.repository.interfaces.ImageInPostRepository;

@Repository
@Transactional
public class ImageInPostRepositoryImpl implements ImageInPostRepository{
    @Autowired
    private LocalSessionFactoryBean sessionFactoryBean;
    
    @Override
    public List<ImageInPost> uploadImage(List<ImageInPost> imageInPosts) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        imageInPosts.forEach(img -> img.setId((Integer) s.save(img)));
        return imageInPosts;
    }

    @Override
    public boolean deleteImageInPost(List<ImageInPost> imageInPosts) {
        Session s = sessionFactoryBean.getObject().getCurrentSession();
        imageInPosts.forEach(img -> {
            ImageInPost persistImageInPost = s.get(ImageInPost.class, img.getId());
            System.out.println("GOT PERSIST: " + persistImageInPost);
            try {
                s.delete(persistImageInPost);
            } catch (HibernateException ex) {
                ex.printStackTrace();
            }
        });
        return true;
    }
    
}
