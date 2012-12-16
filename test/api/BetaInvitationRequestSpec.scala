package test.api

import org.specs2.mutable._

import play.api.test._
import play.api.test.Helpers._
import play.api.libs.json.Json

class BetaInvitationRequestSpec extends Specification { sequential

  "POST create" should {

    "when the submitted email is valid" in {
      running(FakeApplication()) {
        val json = """{"email":"mike@example.com"}"""
        val request = FakeRequest(POST, "/betaInvitationRequest").withHeaders(CONTENT_TYPE -> "application/json", ACCEPT_LANGUAGE -> "en-US")
        val createInvite = route(request, Json.parse(json)).get

        "returns a 200 response" in {
          status(createInvite) must equalTo(OK)
        }

        "returns a success response in JSON format" in {
          contentType(createInvite) must beSome.which(_ == "application/json")
          contentAsString(createInvite) must beEqualTo("{\"success\":true}")
        }
      }
    }

    "when the submitted email is invalid" in {
      running(FakeApplication()) {
        val json = """{"email":" "}"""
        val request = FakeRequest(POST, "/betaInvitationRequest").withHeaders(CONTENT_TYPE -> "application/json", ACCEPT_LANGUAGE -> "en-US")
        val createInvite = route(request, play.api.libs.json.Json.parse(json)).get

        "returns a 400 response" in {
          status(createInvite) must equalTo(BAD_REQUEST)
        }

        "returns an error string" in {
          contentAsString(createInvite) must beEqualTo("{\"email\":[\"error.email\"]}")
        }
      }
    }
  }
}
