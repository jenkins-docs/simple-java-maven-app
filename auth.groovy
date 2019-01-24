def auth(token) {
    def (usr, pass) = token.split(':')
    assert usr == 'banana'
    assert pass == 'apple'
    println "Verification successful."
}

return [
    auth: this.&auth
]