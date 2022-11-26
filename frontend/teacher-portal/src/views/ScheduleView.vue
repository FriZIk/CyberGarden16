<template>
    <div class="schedule">
        <b-table
        striped hover bordered
        :items="items"
        :fields="fields"
        responsive="sm">
            <template v-for="field in fields" v-slot:[`head(${field})`]="data">
                <span v-html="data.field.label" ></span>
            </template>
            <template #cell(l1)="data">
                <a href="#" @click="OpenModalQuizList(data.item, 1)">{{ data.item.l1 }}</a>
            </template>
            <template #cell(l2)="data">
                <a href="#" @click="OpenModalQuizList(data.item, 2)">{{ data.item.l2 }}</a>
            </template>
            <template #cell(l3)="data">
                <a href="#" @click="OpenModalQuizList(data.item, 3)">{{ data.item.l3 }}</a>
            </template>
            <template #cell(l4)="data">
                <a href="#" @click="OpenModalQuizList(data.item, 4)">{{ data.item.l4 }}</a>
            </template>
            <template #cell(l5)="data">
                <a href="#" @click="OpenModalQuizList(data.item, 5)">{{ data.item.l5 }}</a>
            </template>
            <template #cell(l6)="data">
                <a href="#" @click="OpenModalQuizList(data.item, 6)">{{ data.item.l6 }}</a>
            </template>
            <template #cell(l7)="data">
                <a href="#" @click="OpenModalQuizList(data.item, 7)">{{ data.item.l7 }}</a>
            </template>
        </b-table>

        <b-modal id="modal-scoped" scrollable centered button-size="sm" ref="quizlist-modal">
            <template #modal-header="{ createnewquiz }">
                <!-- Emulate built in modal header close button action -->
                <b-button size="sm" variant="outline-success" @click="createNewQuiz()">
                    Создать опрос
                </b-button>
                <h5>Список опросов</h5>
            </template>


            <template #modal-footer="{ cancel }">
                <b-button size="sm" variant="danger" @click="cancel()">
                    Закрыть
                </b-button>
            </template>
        </b-modal>
    </div>
</template>

<script>
import axios from 'axios'
/* eslint-disable */
export default {
    name: 'ScheduleView',
    data() {
        return {
            items: [],
            fields: [
                { key: 'l0', label: 'Пары Время', sortable: false },
                { key: 'l1', label: '1-я 08:00-09:35', sortable: false },
                { key: 'l2', label: '2-я 09:50-11:25', sortable: false },
                { key: 'l3', label: '3-я 11:55-13:30', sortable: false },
                { key: 'l4', label: '4-я 13:45-15:20', sortable: false },
                { key: 'l5', label: '5-я 15:50-17:25', sortable: false },
                { key: 'l6', label: '6-я 17:40-19:15', sortable: false },
                { key: 'l7', label: '7-я 19:30-21:05', sortable: false }
            ],
            clicked_lesson_item: {},
        }
    },
    mounted() {
        document.title = 'Schedule | Teacher Portal'
        this.getTeacherSchedule()
    },
    methods: {
        createNewQuiz() {
            console.log('create new quiz', this.clicked_lesson_item)
        },
        showQuizListModal() {
            this.$refs['quizlist-modal'].show()
        },
        hideQuizListModal() {
            this.$refs['quizlist-modal'].hide()
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
            let d_start_timestamp = d_start.getTime()
            let d_end_timestamp = d_end.getTime()
            // console.log(d_start)
            // console.log(d_end)
            // console.log(d_start_timestamp)
            // console.log(d_end_timestamp)
            
            this.clicked_lesson_item = {
                lessonname: lessonname,
                start_timestamp: d_start_timestamp,
                end_timestamp: d_end_timestamp,
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
</style>