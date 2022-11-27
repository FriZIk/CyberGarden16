<template>
    <b-container class="page-log-in">
        <b-row class="align-items-center" align-v="center">
            <b-col class="align-self-center">
                <b-container class="jumbotron d-flex align-items-center" id="login-form-container">
                    <div class="container">
                        <p id="login-title-text" class="text-center">Авторизация</p>
                        <br/>
                        <div class="justify-content-center">
                            <b-form @submit="submitForm" class="mt-1">
                                <b-form-group
                                    id="input-group-1"
                                    label="Почта"
                                    label-for="input-1-password-loginform"
                                    description=""
                                >
                                    <b-form-input
                                        id="input-1-password-loginform"
                                        v-model="form.email"
                                        required
                                        placeholder="Email"
                                    ></b-form-input>
                                </b-form-group>

                                <b-form-group
                                    id="input-group-2"
                                    label="Пароль"
                                    label-for="input-2-password-loginform"
                                    description=""
                                >
                                    <b-form-input
                                        id="input-2-password-loginform"
                                        type="password"
                                        v-model="form.password"
                                        required
                                        placeholder="Password"
                                        class="mt-3"
                                    ></b-form-input>
                                </b-form-group>
                                <br />
                                <b-button id="login-button" variant="success" class="button is-dark" @click="submitForm()">Вход</b-button>
                            </b-form> 
                        </div>
                    </div>
                </b-container>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
import axios from 'axios'
import { mapMutations } from 'vuex'

export default {
    name: 'LoginView',
    data() {
        return {
            form: {
                email: '',
                password: '',
                errors: []
            },
        }
    },
    mounted() {
        document.title = 'Вход | Портал Преподавателя'
    },
    methods: {
        ...mapMutations(['setTokens',]),
        async submitForm() {
            const formData = {
                email: this.form.email,
                password: this.form.password,
            }
            let a = await axios
                .post('/token/', formData, {
                    headers: {
                        'Content-Type': 'application/json',
                    }
                })
                .then(response => {
                    var accessToken = response.data.access
                    var refreshToken = response.data.refresh

                    this.setTokens({access: accessToken, refresh: refreshToken})

                    this.$router.push({ name: 'schedule' })
                })
                .catch(error => {
                    this.form.errors = []
                    if (error.response) {
                        console.log(JSON.stringify(error))
                        for (const property in error.response.data) {
                            this.form.errors.push(`${property}: ${error.response.data[property]}`)
                        }
                    }
                })

        },
    }
}
</script>

<style scoped>
    #login-form-container {
        height:100%;
        width:45%;

        text-align: center;

        background-color: #FFFFFF;
    }

    #login-form-container > .container {
        /* max-width: 100%;
        
        display: inline-block;
        vertical-align: middle; */
    }

    #login-title-text {
        font-family: 'Inter';
        font-style: normal;
        font-weight: 700;
        font-size: 30px;
        line-height: 120%;
        /* identical to box height, or 36px */

        text-transform: uppercase;

        color: #000000;
    }

    #login-button {
        width: 100%;
        background: #4CAD1E !important;
        border-radius: 20px !important;
        border-color: #4CAD1E;
        height: 3em;

        font-family: 'Nunito' !important;
        font-style: normal !important;
        font-weight: 800 !important;
        font-size: 15px !important;
        line-height: 120% !important;
        /* or 18px */

        text-transform: uppercase;

        color: #FFFFFF;
    }
</style>