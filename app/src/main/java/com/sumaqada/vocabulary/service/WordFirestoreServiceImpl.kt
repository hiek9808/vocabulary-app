package com.sumaqada.vocabulary.service


class WordFirestoreServiceImpl(
    private val firestore: String,
    private val auth: String
) : WordFirestoreService, CrudFirestoreImpl<WordModel>(firestore, auth) {


}