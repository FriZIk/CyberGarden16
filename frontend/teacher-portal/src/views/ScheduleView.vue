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
        </b-table>
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
        }
    },
    mounted() {
        document.title = 'Schedule | Teacher Portal'
        this.getTeacherSchedule()
    },
    methods: {
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
    }
}
</script>

<style>
.table tbody tr th {
  text-align:center;
}
</style>