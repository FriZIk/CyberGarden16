<template>
    <div>
        <b-navbar id="sas-navbar" variant="faded" toggleable="md">
            <b-navbar-brand :to="{name: 'schedule'}">
                <img id="navbar-brand-logo" :src="logoSVG">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Расписание
            </b-navbar-brand>

            <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

            <b-collapse id="nav-collapse" is-nav>
                <b-navbar-nav id="ochko">
                    <b-nav-item :to="{name: 'grade'}">Оценки</b-nav-item>
                    <b-nav-item :to="{name: 'attendance'}">Посещаемость</b-nav-item>
                    <b-nav-item :to="{name: 'involvement'}">Вовлеченность</b-nav-item>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">
                    <b-nav-item right v-if="!isLoggedIn" :to="{name: 'login'}">Вход</b-nav-item>
                    <b-nav-item right v-if="isLoggedIn" @click="logout()"><img id="navbar-account-logout-icon" :src="logoutSVG"></b-nav-item>
                </b-navbar-nav>
            </b-collapse>
        </b-navbar>
    </div>
</template>

<script>
import { mapGetters, mapMutations } from 'vuex';

export default {
    name: 'NavbarComponent',
        data: function () {
            return {
                logoSVG: require('@/assets/Logo_ICTIS_white.svg'),
                logoutSVG: require('@/assets/logouticon1.svg'),
            }
        },
        computed: {
            ...mapGetters(['isLoggedIn']),
        },
        methods: {
            ...mapMutations(['removeTokens', 'verifyRefreshToken']),
            logout() {
                this.removeTokens()
                this.$router.push({ name: 'login' })
            }
        }
    }
</script>

<style scoped>
#navbar-brand-logo {
    width: 6em;
    height: auto;
    margin-right: 0.3em;
    margin-left: 1em;
}

b-navbar {
    background-color: #FFFFFF;
}

.navbar {
    height: 6em !important;
}

.nav-link {
    color: #ffffff !important;
    font-family: 'Inter';
    font-style: normal;
    font-weight: 400;
    font-size: 20px;
    line-height: 30px;
    margin-right: 1em;
    padding-left: 1em !important;
    padding-right: 1em !important;
}

/* .router-link-exact-active {
    background-color: #95E375;
    border-radius: 13px;
} */

.navbar-brand {
    color: #ffffff !important;
    font-family: 'Inter';
    font-style: normal;
    font-weight: 400;
    font-size: 20px;
    line-height: 30px;
    background-color: #00000000 !important;
    margin-left: 0.3em;
}

#nav-collapse {
    /* background-color: #FFFFFF !important; */
    border-radius: 13px;
    padding-top: 0.2em;
    padding-bottom: 0.2em;
    z-index: 1;
    position: relative;
}

#sas-navbar {
    background: linear-gradient(to right, #86AD1E, #1F4890);
}

#ochko {
    margin-left: 1em;
}

#navbar-account-logout-icon {
    fill: white;
    max-width: 2em;
    height: auto;
}

</style>