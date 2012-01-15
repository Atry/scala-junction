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

package com.dongxiguo.junction
import com.sun.jna._
import platform.win32._
import WinNT._
import WinBase._
import Kernel32.INSTANCE._
import java.io.File

object Junction {

  private final val SizeOfReparseDataHead = 8

  private final val SizeOfMountPointReparseHead = 8

  private final val IO_REPARSE_TAG_MOUNT_POINT = 0xA0000003
 
  private final val FSCTL_SET_REPARSE_POINT = 0x000900a4

  def createJunction(link:File, target:File) {
    if (!target.isAbsolute) {
      throw new IllegalArgumentException(
        "Junction must point to an absolute path.")
    }
    if (!link.mkdir()) {
      throw new java.io.IOException("Cannot create " + link)
    }
    mount(link.getPath, target.getPath)
  }

  private final val PathPrefix = """\??\"""

  def mount(link:String, target:String) {
    CreateFile(link,
               GENERIC_READ | GENERIC_WRITE,
               FILE_SHARE_DELETE,
               null,
               OPEN_EXISTING,
               FILE_FLAG_BACKUP_SEMANTICS | FILE_FLAG_OPEN_REPARSE_POINT,
               null) match {
      case INVALID_HANDLE_VALUE =>
        throw new Win32Exception(GetLastError())
      case handle =>
        try {
          val sizeOfPathBuffer =
            java.lang.Character.SIZE / java.lang.Byte.SIZE *
            (PathPrefix.length + target.length + 1 + target.length + 1)
          val sizeOfMoutPointReparseBuffer =
            SizeOfMountPointReparseHead + sizeOfPathBuffer
          val sizeOfReparseDataBuffer =
            SizeOfReparseDataHead + sizeOfMoutPointReparseBuffer
          val reparseData = new Memory(sizeOfReparseDataBuffer)
          val buffer = reparseData.getByteBuffer(0, sizeOfReparseDataBuffer)
          val reserved:Short = 0
          val substituteNameOffset:Short = 0
          val substituteNameLength =
            java.lang.Character.SIZE / java.lang.Byte.SIZE *
            (PathPrefix.length + target.length)
          val printNameOffset =
            substituteNameLength +
            java.lang.Character.SIZE / java.lang.Byte.SIZE
          val printNameLength =
            java.lang.Character.SIZE / java.lang.Byte.SIZE *
            target.length

          buffer.putInt(IO_REPARSE_TAG_MOUNT_POINT)
          buffer.putShort(sizeOfMoutPointReparseBuffer.toShort)
          buffer.putShort(reserved)
          buffer.putShort(substituteNameOffset)
          buffer.putShort(substituteNameLength.toShort)
          buffer.putShort(printNameOffset.toShort)
          buffer.putShort(printNameLength.toShort)
          for (c <- PathPrefix) {
            buffer.putChar(c)
          }
          for (c <- target) {
            buffer.putChar(c)
          }
          buffer.putChar('\0')
          for (c <- target) {
            buffer.putChar(c)
          }
          buffer.putChar('\0')
          if (buffer.position != buffer.capacity) {
            throw new IllegalStateException
          }
          if (!DeviceIoControl(handle,
                               FSCTL_SET_REPARSE_POINT,
                               reparseData,
                               sizeOfReparseDataBuffer,
                               null,
                               0,
                               new ptr.IntByReference,
                               null)) {
            throw new Win32Exception(GetLastError())
          }
        } finally {
          CloseHandle(handle)
        }
    }
  }

}
