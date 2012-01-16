// vim: set softtabstop=2 shiftwidth=2 expandtab:
/*
 * Copyright 2011 杨博 (Yang Bo)
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


package com.dongxiguo.junction.java7

import java.nio.file._
import java.nio.file.attribute.FileAttribute
import com.sun.jna._

/**
 * For Java 7
 */
object Junction {
  def createJunction(link: Path, target: Path,
                     attributes: FileAttribute[_]*): Path = {
    if (!target.isAbsolute) {
      throw new IllegalArgumentException(
        "Junction must point to an absolute path.")
    }
    Files.createDirectory(link, attributes: _*)
    com.dongxiguo.junction.Junction.mount(link.toString, target.toString)
    return link
  }

  def createSymbolicLinkOrJunction(link: Path, target: Path,
                                   attributes: FileAttribute[_]*): Path = {
      try {
        return Files.createSymbolicLink(link, target, attributes: _*)
      } catch {
        case e@ (_: java.io.IOException |
                 _: UnsupportedOperationException) =>
          if (!Platform.isWindows) {
            throw e
          }
          return createJunction(link, target, attributes: _*)
      }
  }

}
