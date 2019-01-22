def auth(token) {
    println token
}

return [
    auth: this.&auth
]