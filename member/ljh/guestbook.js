// guestbook.js
import { db2 } from './firebaseConfig.js';
import { collection, getDocs, addDoc, updateDoc, deleteDoc, doc } from "https://www.gstatic.com/firebasejs/9.22.0/firebase-firestore.js";

export async function addEntry() {
    const nickname = document.getElementById('nickname').value.trim();
    const message = document.getElementById('message').value.trim();
    if (nickname && message) {
        try {
            await addDoc(collection(db2, "guestbook"), { nickname, message, timestamp: new Date() });
            alert("방명록이 성공적으로 등록되었습니다.");
            await renderGuestbook(); // 방명록 다시 렌더링
        } catch (error) {
            console.error("방명록 등록 오류:", error);
        }
        document.getElementById('nickname').value = '';
        document.getElementById('message').value = '';
    } else {
        alert("이름과 댓글을 모두 입력해주세요.");
    }
}

export async function renderGuestbook() {
    const guestbookList = document.getElementById('guestbookList');
    guestbookList.innerHTML = '';
    try {
        const querySnapshot = await getDocs(collection(db2, "guestbook"));
        querySnapshot.forEach((doc) => {
            const entry = doc.data();
            const li = document.createElement('li');
            li.className = 'guestbook-item';
            li.innerHTML = `
                <strong>${entry.nickname}</strong>
                <small>${new Date(entry.timestamp.seconds * 1000).toLocaleString()}</small>
                <p>${entry.message}</p>
                <div class='guestbook-actions'>
                    <button onclick="editEntry('${doc.id}', '${entry.message}')">수정</button>
                    <button onclick="deleteEntry('${doc.id}')">삭제</button>
                </div>`;
            guestbookList.appendChild(li);
        });
    } catch (error) {
        console.error("방명록 불러오기 오류:", error);
    }
}

export async function editEntry(id, currentMessage) {
    const newMessage = prompt("수정할 댓글 내용을 입력하세요:", currentMessage);
    if (newMessage && newMessage.trim()) {
        try {
            await updateDoc(doc(db2, "guestbook", id), { message: newMessage.trim() });
            alert("방명록이 성공적으로 수정되었습니다.");
            await renderGuestbook();
        } catch (error) {
            console.error("방명록 수정 오류:", error);
        }
    } else {
        alert("댓글 내용이 비어있습니다.");
    }
}

export async function deleteEntry(id) {
    if (confirm("정말로 삭제하시겠습니까?")) {
        try {
            await deleteDoc(doc(db2, "guestbook", id));
            alert("방명록 항목이 삭제되었습니다.");
            await renderGuestbook();
        } catch (error) {
            console.error("방명록 삭제 오류:", error);
        }
    }
}