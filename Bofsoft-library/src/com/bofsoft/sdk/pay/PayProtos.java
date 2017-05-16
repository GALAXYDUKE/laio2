package com.bofsoft.sdk.pay;

public final class PayProtos {
    public static final class PayInfoBuf {
        private String sign;
        private String prepayId;

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }
    }
}
