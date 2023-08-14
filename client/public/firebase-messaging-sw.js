// Scripts for firebase and firebase messaging
// eslint-disable-next-line no-undef
importScripts('https://www.gstatic.com/firebasejs/9.0.0/firebase-app-compat.js');
// eslint-disable-next-line no-undef
importScripts('https://www.gstatic.com/firebasejs/9.0.0/firebase-messaging-compat.js');

// Initialize the Firebase app in the service worker by passing the generated config
const firebaseConfig = {
  apiKey: "AIzaSyAUvwNlEKrz7fp8oC1JJldlL29vLrpSwTM",
  authDomain: "ou-social-network-bf1ea.firebaseapp.com",
  projectId: "ou-social-network-bf1ea",
  storageBucket: "ou-social-network-bf1ea.appspot.com",
  messagingSenderId: "587365434814",
  appId: "1:587365434814:web:1af6baafe45803c108a5ef",
  measurementId: "G-01G2X972TF"
};

// eslint-disable-next-line no-undef
firebase.initializeApp(firebaseConfig);

// Retrieve firebase messaging
// eslint-disable-next-line no-undef
const messaging = firebase.messaging();

messaging.onBackgroundMessage(function(payload) {
  console.log('Received background message ', payload);

  const notificationTitle = payload.notification.title;
  const notificationOptions = {
    body: payload.notification.body,
  };

  // eslint-disable-next-line no-restricted-globals
  self.registration.showNotification(notificationTitle,
    notificationOptions);
});
