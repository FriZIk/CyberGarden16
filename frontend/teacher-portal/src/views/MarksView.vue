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
    name: 'MarksView',
    data() {
        return {
            items: [
                {
                    t0: 'Герасимова Александра Викторовна', t1: '89', t2: '92', t3: '-', t4: '76', t5: '100', t6: '89', t7: '-', t8: '-'
                },
                {
                    t0: 'Жалнин Дмитрий Игоревич', t1: '-', t2: '78', t3: '24', t4: '65', t5: '84', t6: '30', t7: '-', t8: '-'
                },
                {
                    t0: 'Кочубей Даниил Сергеевич', t1: '-', t2: '-', t3: '-', t4: '-', t5: '-', t6: '-', t7: '40', t8: '58'
                },
                {
                    t0: 'Кондратов Вадим Сергеевич', t1: '-', t2: '76', t3: '49', t4: '28', t5: '-', t6: '-', t7: '21', t8: '90'
                },
                {
                    t0: 'Ледерер Пётр Алексеевич', t1: '76', t2: '-', t3: '-', t4: '78', t5: '59', t6: '-', t7: '48', t8: '-'
                },
            ],
            fields: [
                { key: 't0', label: 'ФИО / Тесты', sortable: false, class: 'text-center' },
                { key: 't1', label: 'Тест 1', sortable: false, class: 'text-center' },
                { key: 't2', label: 'Тест 2', sortable: false, class: 'text-center' },
                { key: 't3', label: 'Тест 3', sortable: false, class: 'text-center' },
                { key: 't4', label: 'Тест 4', sortable: false, class: 'text-center' },
                { key: 't5', label: 'Тест 5', sortable: false, class: 'text-center' },
                { key: 't6', label: 'Тест 6', sortable: false, class: 'text-center' },
                { key: 't7', label: 'Тест 7', sortable: false, class: 'text-center' },
                { key: 't8', label: 'Тест 8', sortable: false, class: 'text-center' }
            ],
        }
    },
    mounted() {
        document.title = 'Оценки | Портал Преподавателя'
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