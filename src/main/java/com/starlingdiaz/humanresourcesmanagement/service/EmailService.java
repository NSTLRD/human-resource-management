/**
 * @author Starling Diaz on 10/22/2024.
 * @Github https://github.com/NSTLRD
 * @Website https://mentorly.blog/
 * @Academy https://www.mentor-ly.com/
 * @version human-resources-management 1.0
 * @since 10/22/2024.
 */

package com.starlingdiaz.humanresourcesmanagement.service;

import com.starlingdiaz.humanresourcesmanagement.constants.EmailTemplateName;
import jakarta.mail.MessagingException;

public interface EmailService {

    void sendEmail(String to, String username, EmailTemplateName emailTemplate, String ConfirmationUrl, String activationConde, String subject) throws MessagingException;
}