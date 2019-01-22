def auth(token) {
    def (usr, pass) = token.split(':')
    if (usr == 'banana') {
        println "banana"
    }
}

return [
    auth: this.&auth
]