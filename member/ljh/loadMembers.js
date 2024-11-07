// loadMembers.js
import { db1 } from './firebaseConfig.js';
import { collection, getDocs, doc } from "https://www.gstatic.com/firebasejs/9.22.0/firebase-firestore.js";

export async function loadTeamMembers() {
    try {
        const teamMembersContainer = document.getElementById("team-members");
        const querySnapshot = await getDocs(collection(db1, 'members'));

        querySnapshot.forEach((doc) => {
            const memberId = doc.id;
            const member = doc.data();

            if (member.name === "이종현") return; // 특정 멤버 제외

            // 멤버 카드를 생성하여 team-members 컨테이너에 추가
            const memberDiv = document.createElement('div');
            memberDiv.onclick = () => window.location.href = `../../member/${member.initials}/detail_page.html?id=${doc.id}`;
            memberDiv.classList.add('member');
            memberDiv.style.cursor = 'pointer';

            memberDiv.innerHTML = `
                <img src="${member.image_url || '#'}" alt="${member.name}의 사진">
                <p>${member.name}</p>
            `;

            teamMembersContainer.appendChild(memberDiv);
        });
    } catch (error) {
        console.error("Error loading team members:", error);
    }
}