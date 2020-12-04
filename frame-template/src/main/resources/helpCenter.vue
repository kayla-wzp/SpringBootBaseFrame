<template>
    <div id="helpCenter" class="helpCenter-container">
        <iframe name="htmlContext" frameborder="0" ref="contractForm" height="600px" width="1500px"></iframe>

        <el-input @change="changeId()" style="width:250px" v-model="businessId"></el-input>
        <el-button @click="getTest('1')">模板1</el-button>
        <el-button @click="getTest('2')">模板2</el-button>
        <el-button @click="getTest('3')">模板3</el-button>
        <el-button @click="getTest('4')">模板4</el-button>
        <el-button @click="downLoad()">下载</el-button>
        <el-button @click="getFormData">提交</el-button>

    </div>
</template>

<script type="text/ecmascript-6">
    import InfoItem from './infoItem'
    import qs from 'qs';

    export default {
        props: {
            pkgDetail: {
                type: Object
            }
        },
        components: {
            InfoItem
        },
        data() {
            return {
                formData: '', // 获取的表单数据
                spanClick: 'handleSpanClick',//html中需要响应的事件,
                businessId: '10020191225145425101679',
                templateId: '1'
            }
        },
        created() {
            let _this = this
            window[this.spanClick] = (isValid, params) => {
                _this.doSomeThing(isValid, params)
            }
        },

        methods: {
            changeId() {
                this.ajax(
                    this.apiType().getHtmlViewStr,
                    this.serviceType().api,
                    {
                        templateId: id,
                        businessId: this.businessId
                    },
                    responseData => {
                        console.log(responseData)
                        const htmlContent = responseData.body.htmlViewStr
                        htmlContext.document.write(htmlContent)
                        htmlContext.document.close()
                    }
                )
            },
            getTest(id) {
                this.templateId = id;
                this.ajax(
                    this.apiType().getHtmlViewStr,
                    this.serviceType().api,
                    {
                        templateId: id,
                        businessId: this.businessId
                    },
                    responseData => {
                        console.log(responseData)
                        const htmlContent = responseData.body.htmlViewStr
                        htmlContext.document.write(htmlContent)
                        htmlContext.document.close()
                    }
                )
            },
            doSomeThing(isValid, params) {
                //todo
                if (isValid) {
                    debugger
                    console.log(params, '======')
                    this.formData = params
                    debugger
                    let sendParams = {
                        paramStr: JSON.stringify(this.formData),
                        templateId: this.templateId,
                        businessId: this.businessId,
                    }
                    this.newAjax(
                        this.apiType().saveFormDate,
                        this.serviceType().api,
                        sendParams,
                        responseData => {
                            alert("保存成功！！！")
                        }
                    )
                } else {
                    this.$message.error('请检查必填项是否填写正确！')
                }
            },
            getFormData() {
                this.$refs.contractForm.contentWindow.sendFormData()
            },
            downLoad() {
                const params = {
                    templateId: this.templateId,
                    businessId: this.businessId
                }
                const url = this.getUrl('generateContract', params)
                window.location.href = url
            }
        }
    }
</script>

