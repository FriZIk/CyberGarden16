<template>
    <b-container class="align-items-center">
        <b-row class="align-items-center" align-v="center">
            <b-col class="align-self-center align-items-center">
                <h3 class="text-center">{{ quiz_item.quiz_name }}</h3>
                <!-- <p>Вопросы: {{ quiz_item.questions }}</p> -->
                
                <b-card
                    v-for="(question, i) in quiz_item.questions"
                    :key="i"
                    class="question-card"
                >
                    <b-card-text>
                        <b-form-group
                            :label="question.question.questiontext"
                            v-slot="{ ariaDescribedby }"
                        >
                            <b-form-radio
                                v-for="(answer, j) in question.question.answers"
                                :key="j"
                                v-model="selected"
                                :aria-describedby="ariaDescribedby"
                                :name="answer.text"
                                :value="answer.isCorrect"
                                disabled
                            >
                                {{ answer.text }}
                            </b-form-radio>
                        </b-form-group>                
                    </b-card-text>
                </b-card>
            </b-col>
            <b-col class="align-self-center align-items-center">
                <p>Анонимные вопросы</p>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
export default {
    name: "QuizView",
    data() {
        return {
            selected: 'true',
            quiz_item: {}
        }
    },
    mounted() {
        // Получить из локалсторейджа значения текущего quiz_item. Если оно пустое, значит необходимо перейти обратно на страницу расписания
        if (localStorage.getItem('quiz_item') === '') {
            this.$router.push({ name: 'schedule' })
        }

        this.quiz_item = JSON.parse(localStorage.getItem('quiz_item'))
        // Сразу сделать поле questions не строкой, а объектом
        this.quiz_item.questions = JSON.parse(this.quiz_item.questions)

        localStorage.setItem('quiz_item', '');
    },
    methods: {
        
    },
}
</script>

<style scoped>
.question-card {
    margin-bottom: 0.4em;
}
</style>