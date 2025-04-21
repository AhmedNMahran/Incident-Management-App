package com.github.ahmednmahran.elmtickettracking.features.auth.data.model

/**
 * Response model for successful OTP verification
 * @property token JWT authentication token
 * @property roles List of user role IDs
 */
data class LoginResponse(
    val token: String,
    val roles: List<Int>
) {
    companion object {
        /**
         * Default roles in the system:
         * 1 - Supervisor
         * 2 - Worker
         * 3 - Admin
         */
        const val ROLE_SUPERVISOR = 1
        const val ROLE_WORKER = 2
        const val ROLE_ADMIN = 3
    }

    /**
     * Checks if user has a specific role
     */
    fun hasRole(roleId: Int): Boolean = roles.contains(roleId)
}