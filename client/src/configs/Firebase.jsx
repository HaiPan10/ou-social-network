import { initializeApp } from "firebase/app";
import { getMessaging, getToken, onMessage } from "firebase/messaging";

const firebaseConfig = {
    apiKey: "AIzaSyAUvwNlEKrz7fp8oC1JJldlL29vLrpSwTM",
    authDomain: "ou-social-network-bf1ea.firebaseapp.com",
    projectId: "ou-social-network-bf1ea",
    storageBucket: "ou-social-network-bf1ea.appspot.com",
    messagingSenderId: "587365434814",
    appId: "1:587365434814:web:1af6baafe45803c108a5ef",
    measurementId: "G-01G2X972TF"
};

const firebaseApp = initializeApp(firebaseConfig);
const messaging = getMessaging(firebaseApp);

export const fetchToken = (setTokenFound) => {
    return getToken(messaging, {
        vapidKey: "BAbRvN_r74RXISYbzmpxmtgsDdA7pyUyRHC8Ne3iD3aX8TzV_c4irNgBSuOjH8W8WctqGJIplC8pgS-IxooJnoQ",
    })
    .then((currentToken) => {
        if (currentToken) {
            console.log("current token for client: ", currentToken);
            setTokenFound(true);
        } else {
            console.log(
                "No registration token available. Request permission to generate one."
            );
            setTokenFound(false);
        }
    })
    .catch((err) => {
        console.log("An error occurred while retrieving token. ", err);
    });
};

export const onMessageListener = () =>
    new Promise((resolve) => {
        onMessage(messaging, (payload) => {
            resolve(payload);
        });
    });