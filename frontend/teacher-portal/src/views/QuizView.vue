<template>            
    <b-button v-b-modal.modal-prevent-closing>Добавить вопрос</b-button>
    <!-- Под кнопкой список вопросов на текущий квиз
    На другой половине экрана таблица со студентами, сколько кто набрал баллов за прохождение теста -->

    <b-modal
        id="modal-prevent-closing"
        ref="modal"
        title="Add new question"
        @show="resetModal"
        @hidden="resetModal"
        @ok="handleOk"
    >
        <form ref="form" @submit.stop.prevent="handleSubmit">
            <b-form-group
                label="Question"
                label-for="question-input"
                invalid-feedback="Question is required"
                :state="questionState"
            >
                <b-form-input
                    id="question-input"
                    v-model="question"
                    :state="questionState"
                    required
                ></b-form-input>
            </b-form-group>
        </form>
    </b-modal>
</template>

<script>
export default {
    name: 'QuizView',
    data() {
        return {
            question: '',
            questionState: null,
            submittedQuestions: []
        }
    },
    methods: {
        checkFormValidity() {
            const valid = this.$refs.form.checkValidity()
            this.questionState = valid
            return valid
        },
        resetModal() {
            this.question = ''
            this.questionState = null
        },
        handleOk(bvModalEvent) {
            // Prevent modal from closing
            bvModalEvent.preventDefault()
            // Trigger submit handler
            this.handleSubmit()
        },
        handleSubmit() {
            // Exit when the form isn't valid
            if (!this.checkFormValidity()) {
                return
            }
            // Push the questiontext to submitted questiontextes
            this.submittedQuestions.push(this.question)
            // Hide the modal manually
            this.$nextTick(() => {
                this.$bvModal.hide('modal-prevent-closing')
            })
        }
    },
}
</script>

<style>

</style>