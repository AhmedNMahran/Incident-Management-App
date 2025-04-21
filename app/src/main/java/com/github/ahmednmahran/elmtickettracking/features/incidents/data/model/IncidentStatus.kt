package com.github.ahmednmahran.elmtickettracking.features.incidents.data.model

enum class IncidentStatus(val code: Int) {
    SUBMITTED(0),
    IN_PROGRESS(1),
    COMPLETED(2),
    REJECTED(3);

    companion object {
        fun fromCode(code: Int): IncidentStatus {
            return values().find { it.code == code } ?: SUBMITTED
        }
    }
}