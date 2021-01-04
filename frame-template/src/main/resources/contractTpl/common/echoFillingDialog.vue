<template>
    <el-dialog
        :title="title"
        :visible.sync="showEchoFillingDialog"
        width="1000px"
        :append-to-body="true"
        :close-on-click-modal="false"
        @close="clearForm('echoFillingForm')"
    >
        <el-form :model="echoFillingForm" :rules="rules" ref="echoFillingForm">
            <el-form-item label="对象属性名" prop="tplName">
                <el-input v-model="echoFillingForm.tplName"> </el-input>
            </el-form-item>
            <el-form-item label="对象类型" prop="fileType">
                <el-select v-model="echoFillingForm.fileType">
                    <el-option v-for="item in objOptions" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="查询sql" prop="businessSceneId">
                <el-input type="textarea" style="width: 660px" :rows="6" v-model="echoFillingForm.tplName"> </el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" align="center">
            <el-button type="primary">保 存</el-button>
            <el-button @click="showEchoFillingDialog = false">取 消</el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    name: 'echoFillingDialog',
    data() {
        return {
            title: '',
            showEchoFillingDialog: false,
            echoFillingForm: {},
            fileTemplateId: '',
            // 文件类型
            objOptions: [
                {
                    label: 'list<map>',
                    value: '0'
                },
                {
                    label: 'map<String,String>',
                    value: '1'
                },
                {
                    label: 'String',
                    value: '2'
                }
            ],
            rules: {}
        }
    },
    methods: {
        // 打开弹窗
        openDialog(fileTemplateId, id) {
            if (id) {
                this.title = '修改回显填充配置'
            } else {
                this.title = '新增回显填充配置'
            }
            this.showEchoFillingDialog = true
        },
        clearForm(searchForm) {
            this.$refs[searchForm].resetFields()
        }
    }
}
</script>

<style scoped></style>
