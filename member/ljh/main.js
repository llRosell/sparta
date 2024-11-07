// main.js
import { renderGuestbook, addEntry, editEntry, deleteEntry } from './guestbook.js';
import { loadTeamMembers } from './loadMembers.js';

window.addEventListener('DOMContentLoaded', () => {
    renderGuestbook(); // 방명록 초기화
    loadTeamMembers(); // 멤버 카드 로드

    document.getElementById('addEntryButton').addEventListener('click', addEntry);
    window.editEntry = editEntry;
    window.deleteEntry = deleteEntry;
});