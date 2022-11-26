<template>
    <b-container class="page-log-in">
        <b-row class="align-items-center" align-v="center">
            <b-col class="align-self-center">
                <b-container class="jumbotron d-flex align-items-center" id="login-form-container">
                    <div class="container">
                        <p id="login-title-text" class="text-center">Авторизация</p>
                        <br/>
                        <div class="row justify-content-center">
                            <b-form @submit="submitForm" class="mt-1">
                                <b-form-group
                                    id="input-group-1"
                                    label="Email"
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
                                    label="Login"
                                    label-for="input-2-password-loginform"
                                    description=""
                                >
                                    <b-form-input
                                        id="input-2-password-loginform"
                                        type="password"
                                        v-model="form.password"
                                        required
                                        placeholder="..."
                                        class="mt-3"
                                    ></b-form-input>
                                </b-form-group>
                                <br />
                                <b-button id="login-button" variant="success" class="button is-dark" @click="submitForm()">Login</b-button>
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
        document.title = 'Login | Teacher Portal'
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