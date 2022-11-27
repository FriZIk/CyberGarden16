<template>
    <b-container class="grade" fluid="xs">
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
                </b-table>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
/* eslint-disable */
export default {
    name: 'AttendanceView',
    data() {
        return {
            items: [
                {
                    t0: 'Герасимова Александра Викторовна', t1: '+', t2: 'Н', t3: '+', t4: '+', t5: '+', t6: 'Н', t7: '+', t8: '+'
                },
                {
                    t0: 'Жалнин Дмитрий Игоревич', t1: 'Н', t2: '+', t3: 'Н', t4: '+', t5: '+', t6: '+', t7: '+', t8: 'Н'
                },
                {
                    t0: 'Кочубей Даниил Сергеевич', t1: 'Н', t2: 'Н', t3: 'Н', t4: '+', t5: '+', t6: '+', t7: 'Н', t8: '+'
                },
                {
                    t0: 'Кондратов Вадим Сергеевич', t1: '+', t2: 'Н', t3: 'Н', t4: 'Н', t5: '+', t6: '+', t7: '+', t8: 'Н'
                },
                {
                    t0: 'Ледерер Пётр Алексеевич', t1: 'Н', t2: '+', t3: '+', t4: '+', t5: 'Н', t6: '+', t7: 'Н', t8: '+'
                },
            ],
            fields: [
                { key: 't0', label: 'ФИО / Посещаемость', sortable: false, class: 'text-center' },
                { key: 't1', label: 'Пнд,21 ноября', sortable: false, class: 'text-center' },
                { key: 't2', label: 'Втр,22 ноября', sortable: false, class: 'text-center' },
                { key: 't3', label: 'Срд,23 ноября', sortable: false, class: 'text-center' },
                { key: 't4', label: 'Чтв,24 ноября', sortable: false, class: 'text-center' },
                { key: 't5', label: 'Птн,25 ноября', sortable: false, class: 'text-center' },
                { key: 't6', label: 'Сбт,26 ноября', sortable: false, class: 'text-center' },
                { key: 't7', label: 'Вск,27 ноября', sortable: false, class: 'text-center' },
                { key: 't8', label: 'Пнд,28 ноября', sortable: false, class: 'text-center' }
            ],
        }
    },
    mounted() {
        document.title = 'Посещаемость | Портал Преподавателя'
    },
    methods: {
        
    }
}
</script>

<style>
.table tbody tr th {
  text-align:center;
}

.grade {
    margin-left: 2em;
    margin-right: 2em;
    margin-top: 1.6em;
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