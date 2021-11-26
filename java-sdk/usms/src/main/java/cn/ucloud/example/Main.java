package cn.ucloud.example;

import java.util.List;
import javax.validation.constraints.NotEmpty;

import com.google.gson.annotations.SerializedName;

import cn.ucloud.common.pojo.Account;
import cn.ucloud.usms.client.DefaultUSMSClient;
import cn.ucloud.usms.client.USMSClient;
import cn.ucloud.usms.pojo.USMSConfig;
import cn.ucloud.common.annotation.UcloudParam;
import cn.ucloud.common.pojo.BaseRequestParam;
import cn.ucloud.common.pojo.BaseResponseResult;


class SendBatchUSMSMessageParam extends BaseRequestParam {
    @UcloudParam("ProjectId")
    private String projectId;

    @UcloudParam("TaskContent")
    private String taskContent;

    public SendBatchUSMSMessageParam(
            @NotEmpty(message = "projectId can not be empty") String projectId,
            @NotEmpty(message = "taskContent can not be empty") String taskContent
    ) {
        super("SendBatchUSMSMessage");
        this.projectId = projectId;
        this.taskContent = taskContent;
    }
}


class SendBatchUSMSMessageResult extends BaseResponseResult {
    @SerializedName("FailContent")
    private List<BatchInfo> failContent;

    public class BatchInfo {
        @SerializedName("TemplateId")
        private String templateId;

        @SerializedName("SigContent")
        private String sigContent;

        @SerializedName("Target")
        private List<FailPhoneDetail> target;
    }

    public class FailPhoneDetail {
        @SerializedName("TemplateParams")
        private List<String> templateParams;

        @SerializedName("Phone")
        private String phone;

        @SerializedName("ExtendCode")
        private String extendCode;

        @SerializedName("UserId")
        private String userId;

        @SerializedName("FailureDetails")
        private String failureDetails;
    }
}


public class Main {
    public static void main(String[] args) {
        USMSClient client = new DefaultUSMSClient(new USMSConfig(
                new Account(
                        System.getenv("UCLOUD_PRIVATE_KEY"),
                        System.getenv("UCLOUD_PUBLIC_KEY")
                )
        ));

        SendBatchUSMSMessageParam param = new SendBatchUSMSMessageParam(
                System.getenv("UCLOUD_PROJECT_ID"),
                "...."
        );

        SendBatchUSMSMessageResult result = null;
        try {
            result = (SendBatchUSMSMessageResult) client.doAction(param, SendBatchUSMSMessageResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
