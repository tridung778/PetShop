package com.example.ASM.services;

import com.example.ASM.models.Order;
import com.example.ASM.models.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    public static void sendOrderMail(User user, Order order, JavaMailSender mailSender) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setTo(user.getEmail());
        helper.setSubject("Xác nhận email");
        String htmlContent = "<div>\n" +
                "    \n" +
                "    <table cellpadding=\"0\" cellspacing=\"0\" cols=\"1\" bgcolor=\"#d7d7d7\" align=\"center\" style=\"max-width: 600px;\">\n" +
                "        <tr bgcolor=\"#d7d7d7\">\n" +
                "            <td height=\"50\"\n" +
                "                style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "        </tr>\n" +
                "\n" +
                "        \n" +
                "        <tr bgcolor=\"#d7d7d7\">\n" +
                "            <td style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\">\n" +
                "                \n" +
                "                <table cellpadding=\"0\" cellspacing=\"0\" cols=\"1\" bgcolor=\"#d7d7d7\" align=\"center\"\n" +
                "                       style=\"max-width: 600px; width: 100%;\">\n" +
                "                    <tr bgcolor=\"#d7d7d7\">\n" +
                "                        <td height=\"30\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "                \n" +
                "\n" +
                "                \n" +
                "                <table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" cols=\"3\" bgcolor=\"white\"\n" +
                "                       class=\"bordered-left-right\"\n" +
                "                       style=\"border-left: 10px solid #d7d7d7; border-right: 10px solid #d7d7d7; max-width: 600px; width: 100%;\">\n" +
                "                    <tr height=\"50\">\n" +
                "                        <td colspan=\"3\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr align=\"center\">\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                        <td class=\"text-primary\"\n" +
                "                            style=\"color: #F16522; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\">\n" +
                "                            <img src=\"http://dgtlmrktng.s3.amazonaws.com/go/emails/generic-email-template/tick.png\"\n" +
                "                                 alt=\"GO\" width=\"50\"\n" +
                "                                 style=\"border: 0; font-size: 0; margin: 0; max-width: 100%; padding: 0;\">\n" +
                "                        </td>\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr height=\"17\">\n" +
                "                        <td colspan=\"3\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr align=\"center\">\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                        <td class=\"text-primary\"\n" +
                "                            style=\"color: #F16522; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\">\n" +
                "                            <h1 style=\"color: #F16522; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 30px; font-weight: 700; line-height: 34px; margin-bottom: 0; margin-top: 0;\">\n" +
                "                                Đặt hàng thành công</h1>\n" +
                "                        </td>\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr height=\"30\">\n" +
                "                        <td colspan=\"3\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr align=\"left\">\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                        <td style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\">\n" +
                "                            <p style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 22px; margin: 0;\">\n" +
                "                                Chào " + user.getName() + "\n" +
                "                            </p>\n" +
                "                        </td>\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr height=\"10\">\n" +
                "                        <td colspan=\"3\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14" +
                "4px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr align=\"left\">\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                        <td style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\">\n" +
                "                            <p style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 22px; margin: 0;\">\n" +
                "                               Đơn hàng của bạn đã được đặt thành công!</p>\n" +
                "                            <br>\n" +
                "                            <p style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 22px; margin: 0; \">\n" +
                "                                <strong>Hóa đơn chi tiết:</strong><br/>\n" +
                "\n" +
                "                                Giá trị đơn hàng: <fmt:formatNumber value=\"" + order.getTotalPrice() + "\"/> đ<br/>\n" +
                "                                Tài khoản: " + user.getUsername() + ".<br/></p>\n" +
                "                            <br>\n" +
                "                            <p style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 22px; margin: 0;\">\n" +
                "                              Chúng tôi đã gửi thông tin đơn hàng đến email của bạn!<br/></p>\n" +
                "                        </td>\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr height=\"30\">\n" +
                "                        <td style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                        <td style=\"border-bottom: 1px solid #D3D1D1; color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                        <td style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr height=\"30\">\n" +
                "                        <td colspan=\"3\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                    <tr align=\"center\">\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                        <td style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\">\n" +
                "                            <p style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 22px; margin: 0;\">\n" +
                "                                <strong>Transaction reference:</strong></p>\n" +
                "                            <p style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 22px; margin: 0;\">\n" +
                "                               Thời gian đặt hàng: " + order.getOrderDate() + "</p>\n" +
                "                            <p style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 22px; margin: 0;\"></p>\n" +
                "                        </td>\n" +
                "                        <td width=\"36\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                    <tr height=\"50\">\n" +
                "                        <td colspan=\"3\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "\n" +
                "                </table>\n" +
                "                \n" +
                "\n" +
                "                \n" +
                "                <table cellpadding=\"0\" cellspacing=\"0\" cols=\"1\" bgcolor=\"#d7d7d7\" align=\"center\"\n" +
                "                       style=\"max-width: 600px; width: 100%;\">\n" +
                "                    <tr bgcolor=\"#d7d7d7\">\n" +
                "                        <td height=\"50\"\n" +
                "                            style=\"color: #464646; font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 14px; line-height: 16px; vertical-align: top;\"></td>\n" +
                "                    </tr>\n" +
                "                </table>\n" +
                "                \n" +
                "\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "    </table>\n" +
                "    \n" +
                "</div>";

        helper.setText(htmlContent, true);
        mailSender.send(message);
    }
}
