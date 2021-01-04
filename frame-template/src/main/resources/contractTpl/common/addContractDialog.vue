<template>
    <div>
        <el-dialog
            :title="title"
            :visible.sync="showAddContractTpl"
            :close-on-click-modal="false"
            width="600px"
            @close="clearForm('templateForm')"
        >
            <el-form :model="templateForm" :rules="rules" ref="templateForm">
                <el-form-item label="模板名称" prop="fileTemplateName">
                    <el-input v-model="templateForm.fileTemplateName"> </el-input>
                </el-form-item>
                <el-form-item label="文件类型" prop="fileType">
                    <el-select v-model="templateForm.fileType">
                        <el-option
                            v-for="item in fileTypeOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="业务类型" prop="businessType">
                    <el-select v-model="templateForm.businessType">
                        <el-option
                            v-for="item in businessOptions"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value"
                        >
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="模板文件" class="is-required">
                    <el-upload
                        ref="upload"
                        :action="getUploadUrl()"
                        :data="getUploadParam()"
                        :show-file-list="true"
                        name="fileList"
                        :on-success="uploadSuccess"
                        :on-error="uploadErr"
                        :on-preview="handlePreview"
                        :on-remove="handleRemove"
                        :on-progress="uploadFileProcess"
                        :before-upload="beforeAvatarUpload"
                    >
                        <el-button size="small" type="primary" style="width: 150%">点击上传</el-button>
                    </el-upload>
                    <p v-if="isShow" style="margin-block-start: 0;margin-block-end: 0;">
                        <a>{{ templateForm.fileOriginalName }}.{{ templateForm.fileExtension }}</a>
                    </p>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer" align="center">
                <el-button type="primary">保 存</el-button>
                <el-button @click="showAddContractTpl = false">取 消</el-button>
            </div>
        </el-dialog>

        <div class="img-progress" v-if="progressShow">
            <div class="img-progress-content">
                <el-progress type="circle" :percentage="filePercent" style="margin:20px 0"></el-progress>
                <div style="font-size: 14px;color: #26292F">
                    上传中...
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'addContractDialog',
    data() {
        return {
            title: '',
            showAddContractTpl: false,
            fileTemplateId: '',
            templateForm: {
                fileType: '',
                businessType: '',
                fileTemplateName: '',
                fileOriginalName: '',
                fileExtension: ''
            },
            // 文件类型
            fileTypeOptions: [],
            // 业务类型
            businessOptions: [
                {
                    label: '保理',
                    value: '1'
                },
                {
                    label: '转让',
                    value: '2'
                },
                {
                    label: '应收账款',
                    value: '3'
                },
                {
                    label: '贸易合同',
                    value: '4'
                }
            ],
            rules: {},
            isShow: true,
            fileId: '',
            progressShow: false, // 进度条显示隐藏控制
            filePercent: 0 // 进度条
        }
    },
    methods: {
        // 打开弹窗
        openDialog(data) {
            if (data) {
                this.fileTemplateId = data.fileTemplateId
                this.templateForm.fileType = data.fileType
                this.templateForm.businessType = data.businessType
                this.templateForm.fileTemplateName = data.fileTemplateName
                this.templateForm.fileOriginalName = data.fileOriginalName
                this.templateForm.fileExtension = data.fileExtension

                this.isShow = true
                this.title = '修改模板'
            } else {
                this.fileTemplateId = ''
                this.title = '新增模板'
                this.isShow = false
            }
            this.showAddContractTpl = true
        },
        submitForm() {
            this.$refs['templateForm'].validate(valid => {
                if (valid) {
                    const param = {
                        fileTemplateId: this.fileTemplateId,
                        fileId: this.fileId,
                        fileTemplateName: this.templateForm.fileTemplateName,
                        businessType: this.templateForm.businessType,
                        fileType: this.templateForm.fileType
                    }
                    this.ajax(this.apiType().saveDynamicFileTemplate, this.serviceType().api, param, responseData => {
                        this.$message.success('保存成功')
                        this.$emit('saveForm')
                    })
                } else {
                    return false
                }
            })
        },
        clearForm(searchForm) {
            this.fileTemplateId = ''
            this.$refs[searchForm].resetFields()
            this.$refs.upload.clearFiles()
        },
        //************************上传文件*********************
        getUploadParam() {
            let params = {
                fileId: this.fileId
            }
            return Object.assign(this.paramBase(), params)
        },
        // 上传进度
        uploadFileProcess(event, file, fileList) {
            this.progressShow = true
            this.filePercent = 0
            // setTimeout(() => {
            //   this.filePercent = 99
            // }, 1000)
            this.filePercent = parseInt(event.percent.toFixed(0) === 100 ? 99 : event.percent.toFixed(0))
        },
        uploadErr(err) {
            // 关闭loading
            this.progressShow = false
            this.filePercent = 0
            this.$alert(err)
        },
        uploadSuccess(response, file, fileList) {
            // 关闭loading
            let message = response.message
            if (response.resultCode === 'SUCCESS') {
                this.filePercent = 100
                setTimeout(() => {
                    this.progressShow = false
                    this.filePercent = 0
                    this.$message({ showClose: true, message: '文件上传成功!', type: 'success' })
                }, 500)
                this.fileId = response.body.fileId
                this.isShow = false
            } else {
                this.progressShow = false
                this.filePercent = 0
                this.$message({ showClose: true, message: message, type: 'error' })
            }
        },
        beforeAvatarUpload(file) {
            const isHTML = file.type === 'text/html'
            const isLt1M = file.size / 1024 / 1024 < 1
            //选择文件打开loading
            this.progressShow = true
            if (!isHTML) {
                this.$message.error('上传附件只能是 html 格式!')
                this.progressShow = false
                this.filePercent = 0
            }
            if (!isLt1M) {
                this.$message.error('上传附件大小不能超过 1MB!')
                this.progressShow = false
                this.filePercent = 0
            }
            return isHTML && isLt1M
        },
        getUploadUrl() {
            return this.apiUrl().serverPath + this.serviceType().api + '?apiName=' + this.apiType().manageUploadFile
        },
        handleRemove(file, fileList) {
            console.log(file, fileList)
        },
        handlePreview(file) {
            console.log(file)
        }
    }
}
</script>

<style scoped></style>
