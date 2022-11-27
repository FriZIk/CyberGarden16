<template>
    <b-container id="cc" class="align-items-center">
        <b-row>
            <b-col>
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
            <b-col>
                <h3 class="text-center">Анонимные вопросы</h3>
                <b-card
                    v-for="(anon_question, i) in anon_questions"
                    :key="i"
                    class="anon-question-card"
                >
                    <b-card-text>
                        <b-row>
                            <b-col>
                                <img left id="anon-user-profile-logo" :src="anon_user_profile_logo"/>
                            </b-col>
                            <b-col cols="9">
                                <p>{{ anon_question.question }}</p>
                            </b-col>
                        </b-row>
                    </b-card-text>

                    <template #footer>
                        <em>{{ new Date(anon_question.datetime).toLocaleString("ru-RU") }}</em>
                    </template>
                </b-card>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
export default {
    name: "QuizView",
    data() {
        return {
            anon_user_profile_logo: require('@/assets/anon_user_profile_logo.png'),
            selected: 'true',
            quiz_item: {},
            anon_questions: [],
        }
    },
    mounted() {
        document.title = 'Просмотр опроса | Портал Преподавателя'
        // Получить из локалсторейджа значения текущего quiz_item. Если оно пустое, значит необходимо перейти обратно на страницу расписания
        if (localStorage.getItem('quiz_item') === '') {
            this.$router.push({ name: 'schedule' })
        }

        this.quiz_item = JSON.parse(localStorage.getItem('quiz_item'))
        // Сразу сделать поле questions не строкой, а объектом
        this.quiz_item.questions = JSON.parse(this.quiz_item.questions)

        localStorage.setItem('quiz_item', '');

        // Получить из локалсторейджа значения текущих анонимных вопросов anon_questsions. Если оно пустое, то ничего делать не нужно
        if (localStorage.getItem('anon_questions') !== '') {
            this.anon_questions = JSON.parse(localStorage.getItem('anon_questions'))
            
            localStorage.setItem('anon_questions', '');
        }
    },
    methods: {
        
    },
}
</script>

<style scoped>
.question-card {
    margin-bottom: 0.4em;
}

#anon-user-profile-logo {
    max-width: 2.7em;
    height: auto;
    border-radius: 50%;
}

#cc {
    margin-top: 1.6em;
}

</style>