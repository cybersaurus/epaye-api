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

package uk.gov.hmrc.epayeapi.models.in

import org.scalatest.{Matchers, WordSpec}
import TaxYear.extractTaxYear

class TaxYearSpec extends WordSpec with Matchers {
  "Extract tax year" should {
    "retrieve nothing if tax year query string is empty" in {
      extractTaxYear("") shouldBe None
    }
    "retrieve tax year if it matches pattern yyyy-yy" in {
      extractTaxYear("2017-18") shouldBe Some(TaxYear(2017))
    }
    "retrieve nothing if the years don't match" in {
      extractTaxYear("2017-19") shouldBe None
    }
    "retrieve nothing if the years are reversed" in {
      extractTaxYear("2018-17")
    }
    "retrieve nothing if it matches pattern yyyy" in {
      extractTaxYear("2017") shouldBe None
    }
    "retrieve nothing if tax year does not match yyyy-yy" in {
      extractTaxYear("abc") shouldBe None
    }
  }
}
