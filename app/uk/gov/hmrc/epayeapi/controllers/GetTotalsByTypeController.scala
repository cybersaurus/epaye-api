/*
 * Copyright 2017 HM Revenue & Customs
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

package uk.gov.hmrc.epayeapi.controllers

import javax.inject.{Inject, Singleton}

import akka.stream.Materializer
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{Action, EssentialAction}
import uk.gov.hmrc.auth.core.AuthConnector
import uk.gov.hmrc.domain.EmpRef
import uk.gov.hmrc.epayeapi.connectors.EpayeConnector
import uk.gov.hmrc.epayeapi.models.Formats._
import uk.gov.hmrc.epayeapi.models.api.{ApiJsonError, ApiNotFound, ApiSuccess}
import uk.gov.hmrc.epayeapi.models.{ApiError, TotalsByTypeResponse, TotalsResponse}

import scala.concurrent.ExecutionContext

@Singleton
case class GetTotalsByTypeController @Inject()(
  authConnector: AuthConnector,
  epayeConnector: EpayeConnector,
  implicit val ec: ExecutionContext,
  implicit val mat: Materializer
)
  extends ApiController {

  def getTotalsByType(empRef: EmpRef): EssentialAction = EmpRefAction(empRef) {
    Action.async { request =>
      epayeConnector.getTotalsByType(empRef, hc(request)).map {
        case ApiSuccess(totals) =>
          Ok(Json.toJson(TotalsByTypeResponse(empRef, totals)))
        case ApiJsonError(err) =>
          Logger.error(s"Upstream returned invalid json: $err")
          InternalServerError(Json.toJson(ApiError.InternalServerError))
        case ApiNotFound() =>
          NotFound(Json.toJson(ApiError.EmpRefNotFound))
        case error =>
          Logger.error(s"Error while fetching totals: $error")
          InternalServerError(Json.toJson(ApiError.InternalServerError))
      }
    }
  }
}
