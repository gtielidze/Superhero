package com.example.superhero.data.repository

import com.example.superhero.data.models.superhero.PaginatedData
import com.example.superhero.data.models.superhero.SuperheroCard
import com.example.superhero.data.models.user.UserRegistrationRequest
import com.example.superhero.data.network.NetworkClient
import com.example.superhero.data.storage.DataStore
import com.example.superhero.data.storage.db.entities.SavedCardIdEntity
import kotlinx.coroutines.flow.map



object Repository {

    suspend fun getLocalCardById(id: Int): SuperheroCard? {
        return DataStore.db.getCardDAO().getById(id)
    }

    suspend fun getRemoteCardById(id: Int): SuperheroCard {
        return NetworkClient.superheroService.getCardBydId(id).content
            .also {
                DataStore.db.getCardDAO().insert(it)
            }
    }

    suspend fun getRemoteCardsCardsAndStore(
        offset: Int,
        pageSize: Int
    ): PaginatedData<List<SuperheroCard>> {
        val cards = NetworkClient.superheroService.getCards(
            offset = offset,
            pageSize = pageSize
        )
        DataStore.db.getCardDAO().insert(cards.content)
        return cards
    }

    fun getLocalSavedCardsFlow() =
        DataStore.db.getSavedCardsDao().getSavedCardFlow()
            .map { list -> list.map { it.id } }

    suspend fun clearSavedCards() =
        DataStore.db.getSavedCardsDao().deleteAll()


    suspend fun updateRemoteSavedCards(): List<Int> {
        val ids = NetworkClient.userService.getUserCards().map { it }
        DataStore.db.getSavedCardsDao().insert(ids = ids.map { SavedCardIdEntity(it) })
        DataStore.lastTimeSavedCardsFetched = System.currentTimeMillis()
        return ids
    }

    suspend fun loginAndSetToken(username: String, password: String) {
        NetworkClient.userService.login(
            username = username,
            password = password
        ).apply {
            DataStore.authToken = accessToken
        }
    }

    suspend fun registerAndLogin(
        name: String,
        userName: String,
        password: String
    ) {
        NetworkClient.userService.register(
            UserRegistrationRequest(
                name = name,
                userName = userName,
                password = password
            )
        )
        NetworkClient.userService.login(
            username = userName,
            password = password
        ).accessToken.apply {
            DataStore.authToken = this
        }
    }

    suspend fun saveCard(card: SuperheroCard) {
        NetworkClient.userService.saveUserCards(card.id)
        DataStore.db.getSavedCardsDao().insert(SavedCardIdEntity(card.id))
    }

    suspend fun deleteCard(card: SuperheroCard) {
        NetworkClient.userService.deleteUserCard(card.id)
        DataStore.db.getSavedCardsDao().delete(SavedCardIdEntity(card.id))
    }

    suspend fun checkSavedIdsValidity(): Boolean =
        System.currentTimeMillis() - DataStore.lastTimeSavedCardsFetched < 60 * 60 * 1000

    suspend fun invalidateSavedIds() {
        DataStore.lastTimeSavedCardsFetched = 0
    }

    suspend fun getRemoteAndSaveProfile() {
        DataStore.db.getUserProfileDAO().insert(
            NetworkClient.userService.getUser()
        )
    }

    suspend fun clearProfile() {
        DataStore.db.getUserProfileDAO().delete()
    }

    suspend fun getCardsByName(string: String) = NetworkClient.superheroService.searchCards(
        "$string"
    ).content


}