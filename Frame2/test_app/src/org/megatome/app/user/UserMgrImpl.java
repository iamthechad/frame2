/*
 * ====================================================================
 *
 * Frame2 Open Source License
 *
 * Copyright (c) 2004 Virtuas Holdings, Inc.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by
 *        Megatome Technologies."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Frame2 Project", and "Frame2", 
 *    must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact iamthechad@sourceforge.net.
 *
 * 5. Products derived from this software may not be called "Frame2"
 *    nor may "Frame2" appear in their names without prior written
 *    permission of Megatome Technologies.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL MEGATOME TECHNOLOGIES OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.megatome.app.user;

import java.util.ArrayList;
import java.util.List;

public class UserMgrImpl implements UserMgr {

  private static ArrayList _users = new  ArrayList();

  private static String _userAFirstName = "Mike";
  private static String _userALastName = "Day";
  private static String _userAemail = "mday@abc.com";

  private static String _userBFirstName = "Chad";
  private static String _userBLastName = "Johnston";
  private static String _userBemail = "iamthechad@sourceforge.net";


  static {
    String today = User.today();

    User user1 = new User();
    user1.setCreationDate(today);
    user1.setFirstName(_userAFirstName);
    user1.setLastName(_userALastName);
    user1.setEmail(_userAemail);
    
    User user2 = new User();
    user2.setCreationDate(today);
    user2.setFirstName(_userBFirstName);
    user2.setLastName(_userBLastName);
    user2.setEmail(_userBemail);
    
    _users.add(user1);
    _users.add(user2);
  }

  public List getUsers() {
    return _users;
  }

  public void addUser(User user) throws UserException {
    if (user == null) {
      throw new UserException("User is Null");
    }
    _users.add(user);
  }
  
}
