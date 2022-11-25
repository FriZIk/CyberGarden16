<template>
    <div class="login">
        <p>login page</p>
    </div>
</template>

<script>
export default {
    name: 'LoginView',
    data() {
        return {
            from: {
                username: '',
                password: '',
                errors: []
            },
        }
    },
    mounted() {
        document.title = 'Login | Teacher Portal'
    },
    methods: {
        
    }
}
</script>