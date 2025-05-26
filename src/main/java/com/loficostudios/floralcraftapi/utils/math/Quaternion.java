package com.loficostudios.floralcraftapi.utils.math;

public class Quaternion {
//    public float w;
//    public float x;
//    public float y;
//    public float z;
//
//    public Quaternion() {
//        this.w = 1.0F;
//    }
//
//    public Quaternion(float w, float x, float y, float z) {
//        this.w = w;
//        this.x = x;
//        this.y = y;
//        this.z = z;
//    }
//
//    public Quaternion(Quaternion other) {
//        this.w = other.w;
//        this.x = other.x;
//        this.y = other.y;
//        this.z = other.z;
//    }
//
//    public Quaternion(Vector vector, float rotation) {
//        rotation = (float)Math.toRadians((double)rotation);
//        float sinRot = (float)Math.sin((double)(rotation / 2.0F));
//        this.w = (float)Math.cos((double)(rotation / 2.0F));
//        this.x = (float)vector.getX() * sinRot;
//        this.y = (float)vector.getY() * sinRot;
//        this.z = (float)vector.getZ() * sinRot;
//    }
//
//    public Quaternion(Axis axis, float rotation) {
//        this(axis.getVector(), rotation);
//    }
//
//    public Quaternion(float pitch, float yaw, float roll, Angle.Order order) {
//        Quaternion quatX = new Quaternion(new Vector(1.0F, 0.0F, 0.0F), pitch);
//        Quaternion quatY = new Quaternion(new Vector(0.0F, 1.0F, 0.0F), yaw);
//        Quaternion quatZ = new Quaternion(new Vector(0.0F, 0.0F, 1.0F), roll);
//        Quaternion quat = null;
//        switch (order) {
//            case XYZ -> quat = quatX.multiply(quatY).multiply(quatZ);
//            case ZYX -> quat = quatZ.multiply(quatY).multiply(quatX);
//            case YXZ -> quat = quatY.multiply(quatX).multiply(quatZ);
//            case ZXY -> quat = quatZ.multiply(quatX).multiply(quatY);
//            case YZX -> quat = quatY.multiply(quatZ).multiply(quatX);
//            case XZY -> quat = quatX.multiply(quatZ).multiply(quatY);
//        }
//
//        this.w = quat.w;
//        this.x = quat.x;
//        this.y = quat.y;
//        this.z = quat.z;
//    }
//
//    public Quaternion(float pitch, float yaw, float roll) {
//        this(pitch, yaw, roll, Angle.Order.YXZ);
//    }
//
//    public Quaternion(Angle angle) {
//        this(angle.getPitch(), angle.getYaw(), angle.getRoll(), angle.getOrder());
//    }
//
//    public Quaternion(Matrix3f matrix) {
//        this(matrix.m00, matrix.m01, matrix.m02, matrix.m10, matrix.m11, matrix.m12, matrix.m20, matrix.m21, matrix.m22);
//    }
//
//    public Quaternion(Matrix4f matrix) {
//        this(matrix.m00, matrix.m01, matrix.m02, matrix.m10, matrix.m11, matrix.m12, matrix.m20, matrix.m21, matrix.m22);
//    }
//
//    private Quaternion(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
//        float tr = m00 + m11 + m22;
//        if ((double)tr >= (double)0.0F) {
//            float s = (float)Math.sqrt((double)tr + (double)1.0F);
//            this.w = s * 0.5F;
//            s = 0.5F / s;
//            this.x = (m21 - m12) * s;
//            this.y = (m02 - m20) * s;
//            this.z = (m10 - m01) * s;
//        } else {
//            float max = Math.max(Math.max(m00, m11), m22);
//            if (max == m00) {
//                float s = (float)Math.sqrt((double)(m00 - (m11 + m22)) + (double)1.0F);
//                this.x = s * 0.5F;
//                s = 0.5F / s;
//                this.y = (m01 + m10) * s;
//                this.z = (m20 + m02) * s;
//                this.w = (m21 - m12) * s;
//            } else if (max == m11) {
//                float s = (float)Math.sqrt((double)(m11 - (m22 + m00)) + (double)1.0F);
//                this.y = s * 0.5F;
//                s = 0.5F / s;
//                this.z = (m12 + m21) * s;
//                this.x = (m01 + m10) * s;
//                this.w = (m02 - m20) * s;
//            } else {
//                float s = (float)Math.sqrt((double)(m22 - (m00 + m11)) + (double)1.0F);
//                this.z = s * 0.5F;
//                s = 0.5F / s;
//                this.x = (m20 + m02) * s;
//                this.y = (m12 + m21) * s;
//                this.w = (m10 - m01) * s;
//            }
//        }
//
//    }
//
//    public Quaternion copy() {
//        return new Quaternion(this);
//    }
//
//    public float getW() {
//        return this.w;
//    }
//
//    public void setW(float w) {
//        this.w = w;
//    }
//
//    public float getX() {
//        return this.x;
//    }
//
//    public void setX(float x) {
//        this.x = x;
//    }
//
//    public float getY() {
//        return this.y;
//    }
//
//    public void setY(float y) {
//        this.y = y;
//    }
//
//    public float getZ() {
//        return this.z;
//    }
//
//    public void setZ(float z) {
//        this.z = z;
//    }
//
//    public void normalize() {
//        float norm = (float)Math.sqrt((double)(this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z));
//        if (norm > 0.0F) {
//            this.w /= norm;
//            this.x /= norm;
//            this.y /= norm;
//            this.z /= norm;
//        } else {
//            this.w = 1.0F;
//            this.x = 0.0F;
//            this.y = 0.0F;
//            this.z = 0.0F;
//        }
//
//    }
//
//    public Quaternion normalized() {
//        float norm = (float)Math.sqrt((double)(this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z));
//        float newW;
//        float newX;
//        float newY;
//        float newZ;
//        if (norm > 0.0F) {
//            newW = this.w / norm;
//            newX = this.x / norm;
//            newY = this.y / norm;
//            newZ = this.z / norm;
//        } else {
//            newW = 1.0F;
//            newX = 0.0F;
//            newY = 0.0F;
//            newZ = 0.0F;
//        }
//
//        return new Quaternion(newW, newX, newY, newZ);
//    }
//
//    public Quaternion rotate(Axis axis, float degrees) {
//        return null;
//    }
//
//    public Quaternion multiply(Quaternion other) {
//        float newW = this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z;
//        float newX = this.w * other.x + other.w * this.x + this.y * other.z - this.z * other.y;
//        float newY = this.w * other.y + other.w * this.y - this.x * other.z + this.z * other.x;
//        float newZ = this.w * other.z + other.w * this.z + this.x * other.y - this.y * other.x;
//        return new Quaternion(newW, newX, newY, newZ);
//    }
//
//    public Matrix4f getMatrix() {
//        Matrix4f matrix = new Matrix4f();
//        float sqw = this.w * this.w;
//        float sqx = this.x * this.x;
//        float sqy = this.y * this.y;
//        float sqz = this.z * this.z;
//        float invs = 1.0F / (sqx + sqy + sqz + sqw);
//        matrix.m00 = (sqx - sqy - sqz + sqw) * invs;
//        matrix.m11 = (-sqx + sqy - sqz + sqw) * invs;
//        matrix.m22 = (-sqx - sqy + sqz + sqw) * invs;
//        float tmp1 = this.x * this.y;
//        float tmp2 = this.z * this.w;
//        matrix.m10 = 2.0F * (tmp1 + tmp2) * invs;
//        matrix.m01 = 2.0F * (tmp1 - tmp2) * invs;
//        tmp1 = this.x * this.z;
//        tmp2 = this.y * this.w;
//        matrix.m20 = 2.0F * (tmp1 - tmp2) * invs;
//        matrix.m02 = 2.0F * (tmp1 + tmp2) * invs;
//        tmp1 = this.y * this.z;
//        tmp2 = this.x * this.w;
//        matrix.m21 = 2.0F * (tmp1 + tmp2) * invs;
//        matrix.m12 = 2.0F * (tmp1 - tmp2) * invs;
//        return matrix;
//    }
//
//    public int hashCode() {
//        int hash = 3;
//        hash = 23 * hash + Float.floatToIntBits(this.w);
//        hash = 23 * hash + Float.floatToIntBits(this.x);
//        hash = 23 * hash + Float.floatToIntBits(this.y);
//        hash = 23 * hash + Float.floatToIntBits(this.z);
//        return hash;
//    }
//
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        } else if (this.getClass() != obj.getClass()) {
//            return false;
//        } else {
//            Quaternion other = (Quaternion)obj;
//            if (Float.floatToIntBits(this.w) != Float.floatToIntBits(other.w)) {
//                return false;
//            } else if (Float.floatToIntBits(this.x) != Float.floatToIntBits(other.x)) {
//                return false;
//            } else if (Float.floatToIntBits(this.y) != Float.floatToIntBits(other.y)) {
//                return false;
//            } else {
//                return Float.floatToIntBits(this.z) == Float.floatToIntBits(other.z);
//            }
//        }
//    }
//
//    public Vector multiply(Vector vec) {
//        float num = this.x * 2.0F;
//        float num2 = this.y * 2.0F;
//        float num3 = this.z * 2.0F;
//        float num4 = this.x * num;
//        float num5 = this.y * num2;
//        float num6 = this.z * num3;
//        float num7 = this.x * num2;
//        float num8 = this.x * num3;
//        float num9 = this.y * num3;
//        float num10 = this.w * num;
//        float num11 = this.w * num2;
//        float num12 = this.w * num3;
//        Vector result = new Vector();
//
//        result.setX((1.0F - (num5 + num6)) * (float)vec.getX() + (num7 - num12) * (float)vec.getY() + (num8 + num11) * (float)vec.getZ());
//        result.setY((num7 + num12) * (float)vec.getX() + (1.0F - (num4 + num6)) * (float)vec.getY() + (num9 - num10) * (float)vec.getZ());
//        result.setZ((num8 - num11) * (float)vec.getX() + (num9 + num10) * (float)vec.getY() + (1.0F - (num4 + num5)) * (float)vec.getZ());
//
//        return result;
//    }
//
//    public String toString() {
//        return "Quaternion{w=" + this.w + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
//    }
}
