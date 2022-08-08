package com.glowka.rafal.managingusers.data.mapper

import com.glowka.rafal.managingusers.data.graphql.UserListQuery
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class NodeUserToUserMapperTest {

  lateinit var mapper: NodeUserToUserMapper

  @Before
  fun prepare() {
    mapper = NodeUserToUserMapperImpl()
  }

  @After
  fun finish() {
  }

  @Test
  fun parseTest() {
    // Given
    val USER_ID = 124
    val USER_NAME = "test name"
    val USER_EMAIL = "test.name@server.com"

    val dataIn = UserListQuery.Node(
      id = USER_ID,
      name = USER_NAME,
      email = USER_EMAIL,
    )

    // When
    val response = mapper(dataIn)

    // Then
    Assert.assertEquals(USER_ID, response.id)
    Assert.assertEquals(USER_NAME, response.name)
    Assert.assertEquals(USER_EMAIL, response.email)
  }

}