<template>
    <b-container class="schedule" fluid="xs">
        <b-row>
            <b-col>
                <b-table
                striped
                hover
                bordered
                
                :items="items"
                :fields="fields"
                responsive="sm">
                    <template v-for="field in fields" v-slot:[`head(${field})`]="data">
                        <span v-html="data.field.label" ></span>
                    </template>
                    <template #cell(l1)="data">
                        <a href="#" @click="OpenModalQuizList(data.item, 1)" class="cell-lesson">{{ data.item.l1 }}</a>
                    </template>
                    <template #cell(l2)="data">
                        <a href="#" @click="OpenModalQuizList(data.item, 2)" class="cell-lesson">{{ data.item.l2 }}</a>
                    </template>
                    <template #cell(l3)="data">
                        <a href="#" @click="OpenModalQuizList(data.item, 3)" class="cell-lesson">{{ data.item.l3 }}</a>
                    </template>
                    <template #cell(l4)="data">
                        <a href="#" @click="OpenModalQuizList(data.item, 4)" class="cell-lesson">{{ data.item.l4 }}</a>
                    </template>
                    <template #cell(l5)="data">
                        <a href="#" @click="OpenModalQuizList(data.item, 5)" class="cell-lesson">{{ data.item.l5 }}</a>
                    </template>
                    <template #cell(l6)="data">
                        <a href="#" @click="OpenModalQuizList(data.item, 6)" class="cell-lesson">{{ data.item.l6 }}</a>
                    </template>
                    <template #cell(l7)="data">
                        <a href="#" @click="OpenModalQuizList(data.item, 7)" class="cell-lesson">{{ data.item.l7 }}</a>
                    </template>
                </b-table>

                <b-modal id="modal-scoped" scrollable centered button-size="sm" ref="quizlist-modal" hide-footer>
                    <template #modal-header="{ createnewquiz }">
                        <!-- Emulate built in modal header close button action -->
                        <b-button size="sm" variant="outline-success" @click="createNewQuiz()">
                            Создать опрос
                        </b-button>
                        <h5>Список опросов</h5>
                    </template>

                    <b-container class="align-items-center">
                        <b-row>
                            <b-col>
                                <!-- Список имеющихся квизов на эту пару для конкретно нужного мне преподавателя -->
                                <!-- <a
                                    v-for="(quiz, i) in needed_quizes"
                                    :key="i"
                                    @click="goQuizView(quiz)"
                                    class="needed-quiz"
                                >
                                    {{ needed_quizes[i].quiz_name }}
                                    <br />
                                </a> -->
                                <div class="btn-group-vertical justify-content-center" id="needed-quizes-button-group">
                                    <b-button
                                        v-for="(quiz, i) in needed_quizes"
                                        :key="i"
                                        @click="goQuizView(quiz)"
                                        class="needed-quiz"
                                        variant="outline-secondary"
                                    >
                                        {{ needed_quizes[i].quiz_name }}
                                    </b-button>
                                </div>

                                <!-- <template #modal-footer="{ cancel }">
                                    <b-button size="sm" variant="danger" @click="cancel()">
                                        Закрыть
                                    </b-button>
                                </template> -->
                            </b-col>
                        </b-row>
                    </b-container>
                </b-modal>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import axios from 'axios'
import { mapMutations } from 'vuex'
/* eslint-disable */
export default {
    name: 'ScheduleView',
    data() {
        return {
            items: [],
            fields: [
                { key: 'l0', label: 'Пары Время', sortable: false, class: 'text-center' },
                { key: 'l1', label: '1-я 08:00-09:35', sortable: false, class: 'text-center' },
                { key: 'l2', label: '2-я 09:50-11:25', sortable: false, class: 'text-center' },
                { key: 'l3', label: '3-я 11:55-13:30', sortable: false, class: 'text-center' },
                { key: 'l4', label: '4-я 13:45-15:20', sortable: false, class: 'text-center' },
                { key: 'l5', label: '5-я 15:50-17:25', sortable: false, class: 'text-center' },
                { key: 'l6', label: '6-я 17:40-19:15', sortable: false, class: 'text-center' },
                { key: 'l7', label: '7-я 19:30-21:05', sortable: false, class: 'text-center' }
            ],
            clicked_lesson_item: {},
            // Для квизов
            all_quizes: [],
            needed_quizes: [],
            // Для анонимных вопросов
            all_anon_questions: [],
            needed_anon_questions: [],
        }
    },
    mounted() {
        document.title = 'Schedule | Teacher Portal'
        this.getTeacherSchedule()
        this.getAllQuizes()
        this.getAllAnonQuestions()
    },
    methods: {
        ...mapMutations(['setUser',]),
        createNewQuiz() {
            // console.log('create new quiz', this.clicked_lesson_item)
            // Записать в локалстордж данные полученные по клику на предмет
            localStorage.setItem("lesson_item", JSON.stringify(this.clicked_lesson_item))

            this.$router.push({ name: 'quizcreate' })
        },
        goQuizView(quiz) {
            // Поместить в локалстордж нужный квиз.
            localStorage.setItem('quiz_item', JSON.stringify(quiz))

            // Поместить в локалстодж нужные анонимные вопросы, относящиеся к выбранной паре
            localStorage.setItem('anon_questions', JSON.stringify(this.needed_anon_questions))

            this.$router.push({ name: 'quizview' })
        },
        showQuizListModal() {
            this.$refs['quizlist-modal'].show()
        },
        hideQuizListModal() {
            this.$refs['quizlist-modal'].hide()
        },
        async getAllQuizes() {
            let response = await axios.get(`/getquiz/`)
            this.all_quizes = response.data
        },
        async getAllAnonQuestions() {
            let response = await axios.get(`/getanonquestion/`)
            this.all_anon_questions = response.data
        },
        async getTeacherSchedule() {
            const formData = {
                token: this.$store.state.accessToken
            }
            let response = await axios.post('/getuser/', formData, {
                headers: {
                    'Content-Type': 'application/json',
                }
            })
            this.setUser(response.data)
            response = await axios.get(`http://165.22.28.187/schedule-api/?query=${response.data}`)
            response = await axios.get(`http://165.22.28.187/schedule-api/?group=${response.data.table.group}&week=${response.data.table.week}`)
            let tmp_items = []
            for (let i = 2; i < 8; i++) {
                tmp_items.push({
                    l0: response.data.table.table[i][0],
                    l1: response.data.table.table[i][1],
                    l2: response.data.table.table[i][2],
                    l3: response.data.table.table[i][3],
                    l4: response.data.table.table[i][4],
                    l5: response.data.table.table[i][5],
                    l6: response.data.table.table[i][6],
                    l7: response.data.table.table[i][7]
                })
            }
            this.items = tmp_items
        },
        OpenModalQuizList(obj, colid) {
            // Сначала нужно распарсить оьбект и понять к какой дате времени относится пара
            // Определение дня и месяца
            let tmp = obj.l0.split(',')[1]
            tmp = tmp.split(' ')
            let day = tmp[0]
            let month_name = tmp[2]
            // Определение номера пары, то есть даты начала пары и окончания пары, чтобы находить подходящие квизы под эту пару
            // И определение полного названия лекции или практики, на которую кликнули
            let lessonname = null
            let lessonstart = null
            let lessonend = null
            if (colid === 1) {
                lessonstart = '08:00'
                lessonend = '09:35'

                lessonname = obj.l1
            } else if (colid === 2) {
                lessonstart = '09:50'
                lessonend = '11:25'

                lessonname = obj.l2
            } else if (colid === 3) {
                lessonstart = '11:55'
                lessonend = '13:30'

                lessonname = obj.l3
            } else if (colid === 4) {
                lessonstart = '13:45'
                lessonend = '15:20'

                lessonname = obj.l4
            } else if (colid === 5) {
                lessonstart = '15:50'
                lessonend = '17:25'

                lessonname = obj.l5
            } else if (colid === 6) {
                lessonstart = '17:40'
                lessonend = '19:15'

                lessonname = obj.l6
            } else if (colid === 7) {
                lessonstart = '19:30'
                lessonend = '21:05'

                lessonname = obj.l7
            } else {
                // No magic David Blane, no no no!!!
            }
            
            // Сделать из месяца число
            let month = null
            if (month_name.toLowerCase() === 'января') {
                month = 1
            } else if (month_name.toLowerCase() === 'февраля') {
                month = 2
            } else if (month_name.toLowerCase() === 'марта') {
                month = 3
            } else if (month_name.toLowerCase() === 'апреля') {
                month = 4
            } else if (month_name.toLowerCase() === 'мая') {
                month = 5
            } else if (month_name.toLowerCase() === 'июня') {
                month = 6
            } else if (month_name.toLowerCase() === 'июля') {
                month = 7
            } else if (month_name.toLowerCase() === 'августа') {
                month = 8
            } else if (month_name.toLowerCase() === 'сентября') {
                month = 9
            } else if (month_name.toLowerCase() === 'октября') {
                month = 10
            } else if (month_name.toLowerCase() === 'ноября') {
                month = 11
            } else if (month_name.toLowerCase() === 'декабря') {
                month = 12
            }

            // Получить таймштамп по дате и времени
            let year = new Date().getFullYear()
            // console.log(lessonstart, lessonend)
            let d_start = new Date(`${year}-${month}-${day}T${lessonstart.split(':')[0]}:${lessonstart.split(':')[1]}:00`)
            let d_end = new Date(`${year}-${month}-${day}T${lessonend.split(':')[0]}:${lessonend.split(':')[1]}:00`)
            let d_quiz_timestamp = new Date(`${year}-${month}-${day}T${lessonstart.split(':')[0]}:${lessonstart.split(':')[1]}:00`)
            // let d_start_timestamp = d_start.getTime()
            // let d_end_timestamp = d_end.getTime()
            // console.log(d_start)
            // console.log(d_end)
            // console.log(d_start_timestamp)
            // console.log(d_end_timestamp)
            
            this.clicked_lesson_item = {
                lessonname: lessonname,
                quiz_timestamp: d_quiz_timestamp,
                // start_timestamp: d_start,
                // end_timestamp: d_end,
            }

            // Отчистка списка квизов для каждого нового открытия модальной формы с квизами
            this.needed_quizes = []

            // распарсить все квизы, найти нужные
            // найти подходящие по ключу group квизы
            for (let i = 0; i < this.all_quizes.length; i++) {
                // Проверим на groups
                if (this.all_quizes[i].groups === this.clicked_lesson_item.lessonname) {
                    // Проверка на дату
                    const d = new Date(this.all_quizes[i].date)
                    if (d_start.getTime() <= d.getTime() && d.getTime() <= d_end.getTime()) {
                        this.needed_quizes.push(this.all_quizes[i])
                    }
                }
            }

            // распарсить все анонимные вопросы, найти нужные
            // найти подходящие по дате квизы
            
            for (let i = 0; i < this.all_anon_questions.length; i++) {
                // Проверка на дату
                const d = new Date(this.all_anon_questions[i].datetime)
                if (d_start.getTime() <= d.getTime() && d.getTime() <= d_end.getTime()) {
                    this.needed_anon_questions.push(this.all_anon_questions[i])
                }
            }
            

            this.showQuizListModal()
        },
    }
}
</script>

<style>
.table tbody tr th {
  text-align:center;
}

.cell-lesson {
    text-decoration: none;
    color: #161616;
}

.schedule {
    margin-left: 2em;
    margin-right: 2em;
}

/* .needed-quiz {
    margin-bottom: 0.3em;
    text-decoration: none;
}

.needed-quiz:hover, .needed-quiz:active {
    background-color: #4CAD1E;
} */

.needed-quiz {
    /* display: inline-block; */
    margin-bottom: 0.3em;
    /* min-width: 15em; */
    min-width: 100%;
}

#needed-quizes-button-group {
    min-width: 100% !important;
}

</style>