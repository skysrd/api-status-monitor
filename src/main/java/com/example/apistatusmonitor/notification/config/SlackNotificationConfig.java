package com.example.apistatusmonitor.notification.config;

import com.example.apistatusmonitor.notification.sender.NotificationSender;
import com.example.apistatusmonitor.notification.sender.SlackNotificationSender;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "slack_notification_config")
public class SlackNotificationConfig extends NotificationConfig {

    private String webhookUrl;
    @NotNull
    private String channel;
    private String username;
    private String iconEmoji;
    private String iconUrl;
    private String text;
    private String attachments;
    private String blocks;
    private String threadTs;
    private String replyBroadcast;
    private String unfurlLinks;
    private String unfurlMedia;
    private String mrkdwn;
    private String escapeText;
    private String parse;
    private String linkNames;
    private String attachmentsLinkUrl;
    private String attachmentsLinkNames;
    private String attachmentsLinkUnfurl;
    @NotNull
    private String token;

    public void updateConfig(SlackNotificationConfig slackNotificationConfig) {
        this.setName(slackNotificationConfig.getName());
        this.webhookUrl = slackNotificationConfig.getWebhookUrl();
        this.channel = slackNotificationConfig.getChannel();
        this.username = slackNotificationConfig.getUsername();
        this.iconEmoji = slackNotificationConfig.getIconEmoji();
        this.iconUrl = slackNotificationConfig.getIconUrl();
        this.text = slackNotificationConfig.getText();
        this.attachments = slackNotificationConfig.getAttachments();
        this.blocks = slackNotificationConfig.getBlocks();
        this.threadTs = slackNotificationConfig.getThreadTs();
        this.replyBroadcast = slackNotificationConfig.getReplyBroadcast();
        this.unfurlLinks = slackNotificationConfig.getUnfurlLinks();
        this.unfurlMedia = slackNotificationConfig.getUnfurlMedia();
        this.mrkdwn = slackNotificationConfig.getMrkdwn();
        this.escapeText = slackNotificationConfig.getEscapeText();
        this.parse = slackNotificationConfig.getParse();
        this.linkNames = slackNotificationConfig.getLinkNames();
        this.attachmentsLinkUrl = slackNotificationConfig.getAttachmentsLinkUrl();
        this.attachmentsLinkNames = slackNotificationConfig.getAttachmentsLinkNames();
        this.attachmentsLinkUnfurl = slackNotificationConfig.getAttachmentsLinkUnfurl();
        this.token = slackNotificationConfig.getToken();
    }

    @Override
    public NotificationSender createSender() {
        return new SlackNotificationSender(this);
    }
}
