// firebaseConfig.js
import { initializeApp } from "https://www.gstatic.com/firebasejs/9.22.0/firebase-app.js";
import { getFirestore } from "https://www.gstatic.com/firebasejs/9.22.0/firebase-firestore.js";

const firebaseConfig1= {
    apiKey: "AIzaSyAcNxdfupglLDv1Z18wYRBn-AZelw8OhEk",
    authDomain: "introduce-ourselves.firebaseapp.com",
    projectId: "introduce-ourselves",
    storageBucket: "introduce-ourselves.firebasestorage.app",
    messagingSenderId: "510644864006",
    appId: "1:510644864006:web:b252705d880832136c6f87",
    measurementId: "G-SK0R8C7M9R"
};

const firebaseConfig2 = {
    apiKey: "AIzaSyA1dWGt5syRV8TVidWQa4l8CnjqRr8Dc1s",
    authDomain: "sparta-6bf7e.firebaseapp.com",
    projectId: "sparta-6bf7e",
    storageBucket: "sparta-6bf7e.firebasestorage.app",
    messagingSenderId: "534699056839",
    appId: "1:534699056839:web:892a9278e38011918268e2",
    measurementId: "G-KSZ24M0TX5"
};

const app1 = initializeApp(firebaseConfig1);
const app2 = initializeApp(firebaseConfig2, "secondary");

export const db1 = getFirestore(app1);
export const db2 = getFirestore(app2);