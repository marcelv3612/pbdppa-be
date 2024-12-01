package com.bdcga.pbdpp.repository// package com.bdcga.pbdpp.repository

import com.bdcga.pbdpp.model.Session
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface SessionRepository : ReactiveCrudRepository<Session, String>