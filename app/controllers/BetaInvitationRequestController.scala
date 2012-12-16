package controllers

import play.api.mvc._
import play.api.libs.json.Json
import play.api.data.{Form, Forms}

object BetaInvitationRequestController extends Controller {
  val successResponse = Map("success" -> true)

  val betaInvitationRequestMapping = Forms.mapping(
    "email" -> Forms.email
  )(models.BetaInvitationRequest.apply)(models.BetaInvitationRequest.unapply)

  val betaInvitationRequestForm = Form(betaInvitationRequestMapping)

  def create = Action { implicit request =>
      betaInvitationRequestForm.bindFromRequest.fold(
        formWithErrors => BadRequest(formWithErrors.errorsAsJson),
        value => Ok(Json.toJson(successResponse))
      )
  }
}