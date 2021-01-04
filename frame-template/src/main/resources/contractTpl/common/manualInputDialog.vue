<template>
    <el-dialog
        :title="title"
        :visible.sync="showManualInput"
        width="1000px"
        :append-to-body="true"
        :close-on-click-modal="false"
        @close="clearForm('manualInputForm')"
    >
        <el-form :model="manualInputForm" :rules="rules" ref="manualInputForm">
            <el-form-item label="字段名称" prop="tplName">
                <el-input v-model="manualInputForm.tplName"> </el-input>
            </el-form-item>
            <el-form-item label="属性名" prop="tplName">
                <el-input v-model="manualInputForm.tplName"> </el-input>
            </el-form-item>
            <el-form-item label="字段类型" prop="fileType">
                <el-select v-model="manualInputForm.fileType">
                    <el-option
                        v-for="item in fieldTypeOptions"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                    >
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="格式化规则" prop="tplName">
                <el-input v-model="manualInputForm.tplName"> </el-input>
            </el-form-item>
            <el-form-item label="金额转大写对应属性" prop="tplName">
                <el-input v-model="manualInputForm.tplName"> </el-input>
            </el-form-item>
            <el-form-item label="是否必填" prop="tplName">
                <el-radio-group v-model="manualInputForm.resource">
                    <el-radio :label="'1'">是</el-radio>
                    <el-radio :label="'0'">否</el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="默认值" prop="tplName">
                <el-input v-model="manualInputForm.tplName"> </el-input>
            </el-form-item>
            <el-form-item label="校验正则" prop="tplName">
                <el-input v-model="manualInputForm.tplName"> </el-input>
            </el-form-item>
            <el-form-item label="页面展示顺序" prop="tplName">
                <el-input v-model="manualInputForm.tplName"> </el-input>
            </el-form-item>
            <el-form-item label="查询sql" prop="businessSceneId">
                <el-input type="textarea" style="width: 660px" :rows="6" v-model="manualInputForm.tplName"> </el-input>
            </el-form-item>
            <el-form-item label="备注" prop="businessSceneId">
                <el-input type="textarea" style="width: 660px" :rows="6" v-model="manualInputForm.tplName"> </el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" align="center">
            <el-button type="primary">保 存</el-button>
            <el-button @click="showManualInput = false">取 消</el-button>
        </div>
    </el-dialog>
</template>

<script>
export default {
    name: 'manualInputDialog',
    data() {
        return {
            title: '',
            showManualInput: false,
            manualInputForm: {
                fileType: ''
            },
            // 文件类型
            fieldTypeOptions: [
                {
                    label: '日期',
                    value: '0'
                },
                {
                    label: '枚举',
                    value: '1'
                },
                {
                    label: '输入框',
                    value: '2'
                },
                {
                    label: 'SQL',
                    value: '3'
                }
            ],
            rules: {}
        }
    },
    methods: {
        // 打开弹窗
        openDialog(id) {
            if (id) {
                this.title = '修改回显填充配置'
            } else {
                this.title = '新增回显填充配置'
            }
            this.showManualInput = true
        },
        clearForm(searchForm) {
            this.$refs[searchForm].resetFields()
        }
    }
}
</script>

<style scoped></style>
