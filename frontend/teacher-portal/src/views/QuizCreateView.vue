<template>
    <b-container class="align-items-center">
        <b-row class="align-items-center" align-v="center">
            <b-col class="align-self-center align-items-center">
                <b-form @submit="onSubmitQuizForm" @reset="onResetQuizForm">
                    <b-form-group
                        id="quiz-titletext-input-group-1"
                        label="Название"
                        label-for="quiz-titletext"
                    >
                        <b-form-input
                            id="quiz-titletext-input-1"
                            v-model="quiz_form.quiz_titletext"
                            placeholder="Напишите название опроса"
                            required
                        ></b-form-input>
                    </b-form-group>

                    <b-form-group
                        id="questions-input-group-1"
                        label="Вопросы:"
                        label-for="questions"
                    >
                        <p
                            v-for="(question, i) in quiz_form.questions"
                            :key="i"
                            :id="quiz_form.questions[i].id.toString()"
                            
                        >{{ quiz_form.questions[i].question.questiontext }}</p>

                        <b-button @click="addNewQuestion">Добавить вопрос</b-button>
                    </b-form-group>

                    <b-button type="submit" variant="primary">Создать опрос</b-button>
                </b-form>

                <!-- <b-button @click="createQuestionModalOpen">Open Modal</b-button> -->
                <!-- <b-button v-b-modal.modal-prevent-closing-question>Open Modal</b-button> -->

                <b-modal
                    id="modal-prevent-closing-question"
                    scrollable
                    centered
                    button-size="sm"
                    ref="questioncreate-modal"
                    title="Создание вопроса"
                    @show="resetQuestionModal"
                    @hidden="resetQuestionModal"
                    @ok="handleOkQuestionModal"
                >
                    <!-- <b-form @submit="onSubmitQuizForm" @reset="onResetQuizForm"> -->
                    <b-form ref="question-form" @submit.stop.prevent="handleSubmitQuestionForm">
                        <b-form-group
                            id="questiontext-input-group-1"
                            label="Вопрос:"
                            label-for="questiontext"
                        >
                            <b-form-input
                                id="questiontext-input-1"
                                v-model="question_form.questiontext"
                                placeholder="Напишите вопрос"
                                required
                            ></b-form-input>
                        </b-form-group>

                        <b-form-group
                            id="answers-input-group-1"
                            label="Ответы:"
                            label-for="answers"
                        >
                            <b-form-input
                                v-for="(answer, i) in question_form.answers"
                                :key="i"
                                :id="question_form.answers[i].id.toString()"
                                v-model="question_form.answers[i].text"
                                required
                            ></b-form-input>

                            <b-button @click="addNewAnswer">Добавить ответ</b-button>
                        </b-form-group>

                        <!-- <b-button type="submit" variant="primary">Создать вопрос</b-button> -->
                    </b-form>

                </b-modal>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import axios from 'axios'

export default {
    name: 'QuizCreateView',
    data() {
        return {
            quiz_form: {
                quiz_titletext: "",
                lesson_item: {},
                questions: [],
            },
            question_form: {
                questiontext: "",
                answers: [
                    {
                        id: 1,
                        text: "",
                        isCorrect: true,
                    }
                ],
            }
        }
    },
    mounted() {
        // Если нет предмета, то вернуть на страницу с расписанием
        // console.log(localStorage.getItem('lesson_item'))
        if (localStorage.getItem("lesson_item") === '') {
            this.$router.push({ name: 'schedule' })
        }

        // Получить данные ранее нажатого предмета на странице с расписанием
        this.quiz_form.lesson_item = JSON.parse(localStorage.getItem("lesson_item"))
        console.log(this.quiz_form.lesson_item)
        localStorage.setItem('lesson_item', '')
    },
    methods: {
        addNewAnswer() {
            this.question_form.answers.push({
                id: this.question_form.answers[this.question_form.answers.length-1].id+1,
                text: "",
                isCorrect: false,
            })
        },
        addNewQuestion() {
            // Вызов модальной формы с созданием вопроса
            this.createQuestionModalOpen()
        },
        onSubmitQuizForm(event) {
            event.preventDefault()
            // alert(JSON.stringify(this.quiz_form))
            // Логика формирования двух json'ов для бекенд сервера, один без ответов для мобилки, второй с ответами для бекенд сервера
            const formData = {
                quiz_name: this.quiz_form.quiz_titletext,
                questions: JSON.stringify(this.quiz_form.questions),
                teacher: this.$store.state.user,
                date: this.quiz_form.lesson_item.quiz_timestamp,
                groups: this.quiz_form.lesson_item.lessonname,
            }
            console.log(formData)
            axios.post('/createquiz/', formData, {
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            .then(response => {
                console.log(response)

                this.$router.push({ name: 'schedule' })
            })
            .catch(error => {
                console.log(JSON.stringify(error))
            })
        },
        onResetQuizForm(event) {
            event.preventDefault()
            // Reset our question_form values
            this.quiz_form.quiz_titletext = ''
            this.quiz_form.questions = []
        },
        resetQuestionModal() {
            // bvModalEvent.preventDefault()
            // Reset our question_form values
            this.question_form.questiontext = ''
            this.question_form.answers = [{
                id: 1,
                text: "",
                isCorrect: true,
            }]
        },
        handleOkQuestionModal(BvModalEvent) {
            BvModalEvent.preventDefault()
            this.handleSubmitQuestionForm()
        },
        handleSubmitQuestionForm() {
            // console.log(JSON.stringify(this.question_form))
            // alert(JSON.stringify(this.question_form))
            if (this.quiz_form.questions.length > 0) {
                this.quiz_form.questions.push({
                    id: this.quiz_form.questions[this.quiz_form.questions.length-1].id+1,
                    question: JSON.parse(JSON.stringify(this.question_form)),
                })
            } else {
                this.quiz_form.questions.push({
                    id: 1,
                    question: JSON.parse(JSON.stringify(this.question_form)),
                })
            }
            // this.quiz_form.questions[this.quiz_form.questions.length-1].question = this.question_form

            // this.resetQuestionModal()
            // this.$refs['questioncreate-modal'].hide()
            this.$nextTick(() => {
                this.$bvModal.hide('modal-prevent-closing-question')
            })
        },
        createQuestionModalOpen() {
            this.$refs['questioncreate-modal'].show()
        },
    },
}
</script>

<style>

</style>