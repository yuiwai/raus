package com.yuiwai.raus.infrastructure

import scala.concurrent.Future
import com.yuiwai.raus.model.User
import utest._

object PersistenceTest extends TestSuite {
    val tests = Tests {
        "inmemory" - {
            class TestPersistence extends Persistence[Id] {
                def save(key: String, user: User)
                (implicit storage: PersistentStorage[Id]): Unit = saveUser(key, user)
            }
            class TestAsyncPersistence extends Persistence[Future] {
                def save(key: String, user: User)
                (implicit storage: PersistentStorage[Future]): Future[Unit] = saveUser(key, user)
            }
            implicit val storage = new InMemoryStorage[String] {
                def serialize(user: User): String = "user"
                def deserialize(data: String): User = User()
            }
            implicit val asyncStorage = new AsyncInMemoryStorage[String] {
                def serialize(user: User): String = "user"
                def deserialize(data: String): User = User()
            }
            new TestPersistence().save("test", User())
            new TestAsyncPersistence().save("test", User())
        }
    }
}