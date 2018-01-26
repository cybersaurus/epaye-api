/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.epayeapi.models.out

case class ApiErrorJson(code: String, message: String)

object ApiErrorJson {

  def GenericAuthorizationError(message: String): ApiErrorJson =
    ApiErrorJson("AUTHORIZATION_ERROR", message)

  object AuthorizationHeaderInvalid extends ApiErrorJson(
    "AUTHORIZATION_HEADER_INVALID",
    "You must provide a valid token in your header"
  )

  object TokenExpired extends ApiErrorJson(
    "TOKEN_EXPIRED",
    "You must provide a valid authorization token in your header"
  )

  object InsufficientEnrolments extends ApiErrorJson(
    "INSUFFICIENT_ENROLMENTS",
    "You are not currently enrolled for ePAYE"
  )

  object InvalidEmpRef extends ApiErrorJson(
    "INVALID_EMPREF",
    "Provided EmpRef is not associated with your account"
  )

  object EmpRefNotFound extends ApiErrorJson(
    "EMPREF_NOT_FOUND",
    "Provided EmpRef wasn't found."
  )

  object InternalServerError extends ApiErrorJson(
    "INTERNAL_SERVER_ERROR",
    "We are currently experiencing problems. Please try again later."
  )

  object GatewayError extends ApiErrorJson(
    "GATEWAY_ERROR",
    "We are currently experiencing problems with upsteam responses. Please try again later."
  )
}
