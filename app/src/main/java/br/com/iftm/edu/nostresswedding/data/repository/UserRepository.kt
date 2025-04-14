package br.com.iftm.edu.nostresswedding.data.repository

import br.com.iftm.edu.nostresswedding.data.local.database.NSWeddingDatabase
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val db : NSWeddingDatabase
) {

}