/*
 * SonarQube Web Plugin
 * Copyright (C) 2010 SonarSource and Matthijs Galesloot
 * dev@sonar.codehaus.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.sonar.plugins.web.checks.structure;

import org.sonar.plugins.web.checks.TestHelper;

import org.junit.Rule;
import org.junit.Test;
import org.sonar.plugins.web.checks.CheckMessagesVerifierRule;
import org.sonar.plugins.web.visitor.WebSourceCode;

import java.io.File;

import static org.fest.assertions.Assertions.assertThat;

public class ChildElementRequiredCheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void detected() {
    ChildElementRequiredCheck check = new ChildElementRequiredCheck();
    assertThat(check.child).isEmpty();
    assertThat(check.parent).isEmpty();
  }

  @Test
  public void custom() {
    ChildElementRequiredCheck check = new ChildElementRequiredCheck();
    check.child = "bar";
    check.parent = "foo";

    WebSourceCode sourceCode = TestHelper.scan(new File("src/test/resources/checks/ChildElementRequiredCheck.html"), check);

    checkMessagesVerifier.verify(sourceCode.getViolations())
        .next().atLine(8).withMessage("Add the missing \"bar\" element to this \"foo\".")
        .next().atLine(12);
  }

}
