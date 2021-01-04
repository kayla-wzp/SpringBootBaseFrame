<template>
    <el-dialog
        :title="title"
        :visible.sync="showEnumDialog"
        width="1200px"
        :append-to-body="true"
        :close-on-click-modal="false"
        @close="clearForm('enumForm')"
    >
        <el-form :model="enumForm" ref="enumForm" label-width="120px">
            <div v-for="(domain, index) in enumForm.enumData" :key="domain.key" style="display: flex;">
                <el-form-item
                    label="枚举key"
                    :prop="'enumData.' + index + '.enumKey'"
                    :rules="{
                        required: true,
                        message: '枚举key不能为空',
                        trigger: 'blur'
                    }"
                >
                    <el-input v-model="domain.enumKey" style="width:200px"></el-input>
                </el-form-item>
                <el-form-item
                    label="枚举值"
                    :prop="'enumData.' + index + '.value'"
                    :rules="{
                        required: true,
                        message: '枚举值不能为空',
                        trigger: 'blur'
                    }"
                >
                    <el-input v-model="domain.value" style="width:200px"></el-input>
                </el-form-item>
                <el-form-item
                    label="页面展示顺序"
                    :prop="'enumData.' + index + '.sort'"
                    :rules="{
                        required: true,
                        message: '枚举值不能为空',
                        trigger: 'blur'
                    }"
                >
                    <el-input v-model="domain.sort" style="width:200px"></el-input>
                    <el-button type="warning" plain @click.prevent="removeDomain(domain)">删除</el-button>
                </el-form-item>
            </div>
        </el-form>
        <div slot="footer" class="dialog-footer" align="center">
            <el-button type="primary" @click="addDomain">新增</el-button>
            <el-button type="primary" @click="submitForm('enumForm')">保 存</el-button>
            <el-button @click="showEnumDialog = false">取 消</el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    name: 'enumDialog',
    data() {
        return {
            title: '',
            showEnumDialog: false,
            enumForm: {
                enumData: [
                    {
                        enumKey: '',
                        value: '',
                        sort: ''
                    }
                ]
            }
        }
    },
    methods: {
        // 打开弹窗
        openDialog(id) {
            if (id) {
                this.title = '修改枚举配置'
            } else {
                this.title = '新增枚举配置'
            }
            this.showEnumDialog = true
        },
        clearForm(searchForm) {
            this.$refs[searchForm].resetFields()
            this.enumForm = {
                enumData: [
                    {
                        enumKey: '',
                        value: '',
                        sort: ''
                    }
                ]
            }
        },
        // 保存
        submitForm(formName) {
            this.$refs[formName].validate(valid => {
                if (valid) {
                    console.log(this.enumForm.enumData)
                } else {
                    console.log('error submit!!')
                    return false
                }
            })
        },
        // 删除
        removeDomain(item) {
            var index = this.enumForm.enumData.indexOf(item)
            if (index !== -1) {
                this.enumForm.enumData.splice(index, 1)
            }
        },
        // 新增
        addDomain() {
            this.enumForm.enumData.push({
                enumKey: '',
                value: '',
                sort: '',
                key: Date.now()
            })
        }
    }
}
</script>
