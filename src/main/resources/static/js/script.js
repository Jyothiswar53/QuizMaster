document.addEventListener('DOMContentLoaded', function () {
    const quizForm = document.querySelector('#quizForm');
    if (!quizForm) return;

    quizForm.addEventListener('submit', async function (e) {
        e.preventDefault();

        const difficulty = quizForm.querySelector('input[name="difficulty"]').value;

        const questionIds = [];
        const answers = [];

        document.querySelectorAll('input[name^="questionIds"]').forEach(input => {
            questionIds.push(Number(input.value));
        });

        const answerMap = {};
        document.querySelectorAll('input[type="radio"]').forEach(input => {
            const match = input.name.match(/answers\[(\d+)\]/);
            if (match && input.checked) {
                const index = Number(match[1]);
                answerMap[index] = input.value;
            }
        });

        for (let i = 0; i < questionIds.length; i++) {
            answers.push(answerMap[i] || "");
        }

        const submission = { difficulty, questionIds, answers };

        const response = await fetch('/quiz/submit', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(submission)
        });

        if (response.ok) {
            const result = await response.text();
            if (result === "SUCCESS") {
                window.location.href = '/result';
            }
        } else {
            alert("Error submitting quiz. Please try again.");
        }
    });
});