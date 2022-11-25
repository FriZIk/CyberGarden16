<template>
    <div class="schedule">
        <b-table striped hover :items="teacher_schedule_items"></b-table>
    </div>
</template>

<script>
import axios from 'axios'
/* eslint-disable */
export default {
    name: 'ScheduleView',
    data() {
        return {
            teacher_schedule_items: []
        }
    },
    mounted() {
        document.title = 'Schedule | Teacher Portal'
        this.getTeacherSchedule()
    },
    methods: {
        async getTeacherSchedule() {
            let response = await axios.get('http://165.22.28.187/schedule-api/?query=Елькин')
            let schedule_table_items = []
            for (let i = 2; i < response.data.table.table.length; i++) {
                
            }
        },
    }
}
</script>