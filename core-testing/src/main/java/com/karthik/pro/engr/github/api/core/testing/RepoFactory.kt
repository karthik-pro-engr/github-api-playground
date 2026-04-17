package com.karthik.pro.engr.github.api.core.testing

object RepoFactory {
    fun defaultRepo() = FakeRepo.repo()
    fun withId(id: Long) = FakeRepo.repo(id = id)
}