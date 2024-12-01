package com.bdcga.pbdpp.repository

import com.bdcga.pbdpp.model.Feedback
import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface FeedbackRepository : ReactiveCrudRepository<Feedback, String>