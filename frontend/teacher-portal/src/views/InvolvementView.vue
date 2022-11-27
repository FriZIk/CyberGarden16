<template>
    <b-container class="involvement" fluid="xs">
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
    name: 'InvolvementView',
    data() {
        return {
            items: [
                {
                    t0: 'Герасимова Александра Викторовна', t1: '0.845', t2: '-', t3: '0.723', t4: '0.987', t5: '0.867', t6: '0.623', t7: '-', t8: '0.889'
                },
                {
                    t0: 'Жалнин Дмитрий Игоревич', t1: '0.612', t2: '-', t3: '0.857', t4: '0.887', t5: '-', t6: '0.526', t7: '-', t8: '0.518'
                },
                {
                    t0: 'Кочубей Даниил Сергеевич', t1: '0.474', t2: '-', t3: '0.819', t4: '-', t5: '0.309', t6: '-', t7: '0.935', t8: '0.717'
                },
                {
                    t0: 'Кондратов Вадим Сергеевич', t1: '0.576', t2: '0.924', t3: '-', t4: '0.376', t5: '0.787', t6: '-', t7: '0.492', t8: '0.814'
                },
                {
                    t0: 'Ледерер Пётр Алексеевич', t1: '0.445', t2: '-', t3: '0.978', t4: '-', t5: '0.587', t6: '0.835', t7: '-', t8: '0.987'
                },
            ],
            fields: [
                { key: 't0', label: 'ФИО / Вовлеченность', sortable: false, class: 'text-center' },
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
        document.title = 'Вовлеченность | Портал Преподавателя'
    },
    methods: {
        
    }
}
</script>

<style>
.table tbody tr th {
  text-align:center;
}

.involvement {
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